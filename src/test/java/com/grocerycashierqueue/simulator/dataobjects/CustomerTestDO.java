package com.grocerycashierqueue.simulator.dataobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.grocerycashierqueue.simulator.actors.Customer;
import com.grocerycashierqueue.simulator.actors.CustomerType;

/**
 * Creates test data for different scenarios
 *
 * @author HEKEDAR.
 *         Created Aug 13, 2018.
 */
public class CustomerTestDO {
	
	/**
	 * Get the list of customer having different number of items.
	 *
	 * @return list of customer with different number of items
	 */
	public static List<Customer> getCustomerListWithDifferentNumberOfItems(){
		Customer cust1 = new Customer(CustomerType.A, 3);
		Customer cust2 = new Customer(CustomerType.B, 4);
		Customer cust3 = new Customer(CustomerType.B, 1);
		Customer cust4 = new Customer(CustomerType.A, 2);
		return new ArrayList<Customer>(Arrays.asList(cust1,cust2,cust3,cust4));
	}
	
	/**
	 * Get the list of customers purchasing same number of items
	 * @return List of {@linkplain Customer}
	 */
	public static List<Customer> getCustomerListWithSameItemNumbers(){
		Customer cust2 = new Customer(CustomerType.B, 2);
		Customer cust3 = new Customer(CustomerType.A, 2);
		Customer cust4 = new Customer(CustomerType.A, 2);
		return new ArrayList<Customer>(Arrays.asList(cust2,cust3,cust4));
	}
	/**
	 * Get the list of customers arriving at same time at registers
	 * @return list of customers
	 */
	public static List<Customer> getCustomerListArrivingAtSameTime(){
		Customer cust1 = new Customer(CustomerType.A, 3);
		Customer cust2 = new Customer(CustomerType.B, 4);
		Customer cust3 = new Customer(CustomerType.A, 2);
		return new ArrayList<Customer>(Arrays.asList(cust1,cust2,cust3));
	}
}
