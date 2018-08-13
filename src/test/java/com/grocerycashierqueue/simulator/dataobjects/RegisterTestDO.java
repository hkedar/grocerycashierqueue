package com.grocerycashierqueue.simulator.dataobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.grocerycashierqueue.simulator.actors.Customer;
import com.grocerycashierqueue.simulator.actors.CustomerType;
import com.grocerycashierqueue.simulator.actors.Register;
import com.grocerycashierqueue.simulator.buss.RegisterBuss;

/**
 * Creates data for testing Register
 *
 * @author HEKEDAR.
 *         Created Aug 13, 2018.
 */
public class RegisterTestDO {
	/**
	 * Get list of unsorted list of Register
	 *
	 * @return List<Register>
	 */
	public static List<Register> getListOfRegisters(){
		return new ArrayList<Register>(Arrays.asList(
				new Register(3, false),
				new Register(1, false),
				new Register(2, false),
				new Register(4, true)));
	}
	
	/**
	 * Get list of register with different number customer queue
	 *
	 * @return List of register with customer queue
	 * @throws IllegalAccessException
	 */
	public static List<Register> getListOfRegisterWithCustomers() throws IllegalAccessException{
		Queue<Customer> custList1 = new LinkedList<Customer>();
		custList1.offer(new Customer(CustomerType.A, 2));
		custList1.offer(new Customer(CustomerType.B, 2));
		
		Queue<Customer> custList2 = new LinkedList<Customer>();
		custList2.offer(new Customer(CustomerType.A, 1));
		
		Register register1 = new Register(1, false);
		FieldUtils.writeDeclaredField(register1, "customerQueue", custList1, true);
		
		Register register2 = new Register(2, true);
		FieldUtils.writeDeclaredField(register2, "customerQueue", custList2, true);
		
		return Arrays.asList(register1, register2);
	}
	
	/**
	 * Should return registerBuss object with list of Register with customers
	 * @return {@link RegisterBuss}
	 * @throws IllegalAccessException
	 */
	public static RegisterBuss getRegisterBussWithCustomers() throws IllegalAccessException{
		RegisterBuss registerBuss = new RegisterBuss(0);
		FieldUtils.writeDeclaredField(registerBuss, "registerList", getListOfRegisterWithCustomers(), true);
		return registerBuss;
	}
}
