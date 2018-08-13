package com.grocerycashierqueue.simulator.buss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.grocerycashierqueue.simulator.actors.Customer;
import com.grocerycashierqueue.simulator.actors.CustomerType;
import com.grocerycashierqueue.simulator.actors.Register;

/**
 * Has the complete business logic for the registers and maintains the registers list sorted <br>
 * as per their index number.
 *
 * @author HEKEDAR.
 *         Created Aug 12, 2018.
 */
public class RegisterBuss {
	/**
	 * List of registers
	 */
	private List<Register> registerList = new ArrayList<>();
	
	
	/**
	 * Create registers based on the number of registers got from the input file and <br>
	 * add it to register list. This method marks that the trainee cashier is at the last register
	 * 
	 * @param numOfRegisters (require) total number of registers serving customers 
	 */
	public RegisterBuss(int numOfRegisters){
		for(int i=0; i<numOfRegisters; i++){
			if(i < numOfRegisters-1){
				this.registerList.add(new Register(i, false));
			} else {
				this.registerList.add(new Register(i, true));
			}
		}
	}


	/**
	 * Get the list of registers
	 * @return Returns the registerList.
	 */
	public List<Register> getRegisterList() {
		return this.registerList;
	}
	
	/**
	 * Checks if any customer is currently in the queue of the registers 
	 * True: If the customer is in register queue.
	 * False: If the customer queues are empty
	 * @return True/False
	 */
	public boolean isAnyCustomerIsBeingServed() {
        for (Register register : this.registerList) {
            if (!register.getCustomerQueue().isEmpty()){
                return true;
            }
        }
        return false;
    }
	
	/**
	 * Customers are added to the register queue based on their type or number of items they are purchasing 
	 *
	 * @param customerList (require)List of customer entering queue at same time
	 */
	public void startServicingCustomers(List<Customer> customerList) {
        for (Customer customer : customerList) {
        	Register availableRegister = null;
            if (customer.getType().equals(CustomerType.A)) {
                availableRegister = getRegisterWithFewCustomersInQueue();
            } else {
            	availableRegister = getEmptyRegisterOrWithCustomerHavingFewItems();
            }
            availableRegister.getCustomerQueue().offer(customer);
        }
    }
	
	/**
	 * {@link  CustomerType} "A" always chooses the register with the shortest line <br>
	 * (fewest number of customers in line) 
	 *
	 * @return Register with the less number of customers in queue
	 */
	private Register getRegisterWithFewCustomersInQueue(){
		/**
		 * The registerList contain registers sorted in index. 
		 * So a copy has to be made for sorting register based on customers in queue of register. 
		 */
		List<Register> registerListCopy = new ArrayList<>();
		registerListCopy.addAll(this.registerList);
		Collections.sort(registerListCopy, Register.sortByCustomerQueueSize);
		return registerListCopy.get(0);
	}
	
	/**
	 * {@link CustomerType} B looks at the last customer in each line, and always chooses 
	 * to be behind the customer with the fewest number of items left to check out,
	 * regardless of how many other customers are in the line or how many items
	 * they have. Customer Type B will always choose an empty line before a line
	 * with any customers in it.
	 *
	 * @return Register which may be having no customers or customer with least number of items
	 */
	private Register getEmptyRegisterOrWithCustomerHavingFewItems(){
		List<Register> emptyRegisters = new ArrayList<>();
		Map<Customer, Register> customerRegisterMap = new HashMap<>();
		
		for (Register register : this.registerList) {
			if(register.getCustomerQueue().isEmpty()){
				emptyRegisters.add(register);
			} else {
				// Get last customer in register to check the number of items the customer has
				Customer lastCustomer = getLastCustomerInQueue(register.getCustomerQueue());
				customerRegisterMap.put(lastCustomer, register);
			}
		}
		if(!emptyRegisters.isEmpty()){
			Collections.sort(emptyRegisters, Register.sortByRegisterIndex);
			return emptyRegisters.get(0);
		} else {
			List<Customer> lastCustomerInRegisters = new ArrayList<Customer>(customerRegisterMap.keySet());
			Collections.sort(lastCustomerInRegisters, Customer.sortByItemNumbersOrType);
			return customerRegisterMap.get(lastCustomerInRegisters.get(0));
		}
	}
	
	/**
	 * Get the last customer entered in the register queue
	 * 
	 * @param customerList
	 * @return
	 */
	private Customer getLastCustomerInQueue(Queue<Customer> customerList) {
        Customer lastCustomer=null;
        Iterator<Customer> iterator=customerList.iterator();
        while (iterator.hasNext()) {
            lastCustomer=iterator.next();
        }
        return lastCustomer;
	}
	
	/**
	 * Serve customer based on the register
	 *
	 * @param register (required) Register at which the customer is being served
	 */
    public void serveCustomer(Register register){
    	if (register.isCashierTrainee()) {
	    	servedByTranieeCashier(register.getCustomerQueue());
	    } else {
	    	servedByExpertCashier(register.getCustomerQueue());
	    }
    }
    
    /**
	 * Serve the customer when register is of type expert
	 *
	 * @param customer
	 */
	private void servedByTranieeCashier(Queue<Customer> customerQueue) {
		Customer customer = customerQueue.peek();
		if(customer != null && customer.itemsServedByTrainee() == 0){
			customerQueue.poll();
		}
	}
	
	/**
	 * Serve the customer when register is of type trainee
	 *
	 * @param customer
	 */
	private void servedByExpertCashier(Queue<Customer> customerQueue) {
		Customer customer = customerQueue.peek();
		if(customer != null && customer.itemsServedByExpert() == 0){
			customerQueue.poll();
		}
	}
	
}
