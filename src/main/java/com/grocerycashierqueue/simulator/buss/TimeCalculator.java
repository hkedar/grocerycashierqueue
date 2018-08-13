package com.grocerycashierqueue.simulator.buss;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.grocerycashierqueue.simulator.actors.Customer;
import com.grocerycashierqueue.simulator.actors.Register;

/**
 * Calculates the total time taken by the registers to serve all customer
 * @author hekedar. Created Aug 12, 2018.
 */
public class TimeCalculator {
	
	/**<p>
	 * Method to calculate time for given number of customers arriving at queue and total available Registers
	 * The method loops for a time in minutes starting from 1 and increments by 1 and adds customers to empty register. 
	 * Then the serving is done for each minute i.e. for each loop and items are served based on the type of cashier at 
	 * register. After all the items for a customer is served then the customer is removed from the queue. </p>  
	 * 
	 * <p> The time loop continues till the customer list is empty or the registers are finished with serving the last
	 * customer
	 * </p>
	 *
	 * @param registerBuss {@link RegisterBuss} object containing the list of available registers
	 * @param customerQueueWithTimeMapping List of customers mapped against the time they enter the queue
	 * @return total time taken by registers to serve all customers
	 */
	public int calculate(RegisterBuss registerBuss, Map<Integer, List<Customer>> customerQueueWithTimeMapping){
		int time = 1;
		while (!customerQueueWithTimeMapping.isEmpty() || registerBuss.isAnyCustomerIsBeingServed()) {
            List<Customer> listOfCustomerArrivedAtSameTime = customerQueueWithTimeMapping.get(time);
            if(listOfCustomerArrivedAtSameTime != null){ // Customers found at given time
            	customerQueueWithTimeMapping.remove(time);
            	/**
            	 * Sorting customer arriving at same time as per number of items and then type. A will be always before B if the items are same
            	 * if A(3 items) and B(3 items) are coming at same time then A(3items) type will be choose before B(3 items)
            	 */
	            Collections.sort(listOfCustomerArrivedAtSameTime, Customer.sortByItemNumbersOrType);
	            registerBuss.startServicingCustomers(listOfCustomerArrivedAtSameTime);
            }
            serverCustomerOnEachRegister(registerBuss);
            time++;
        }
		return time;
	}

	/**
	 * Continuously serving customer at the interval of T time.
	 *
	 * @param registerBuss
	 */
	private void serverCustomerOnEachRegister(RegisterBuss registerBuss) {
		for (Register register : registerBuss.getRegisterList()) {
			registerBuss.serveCustomer(register);
		}
	}
}
