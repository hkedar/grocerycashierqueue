package com.grocerycashierqueue.simulator.actors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.grocerycashierqueue.simulator.dataobjects.CustomerTestDO;

/**
 * Unit test case for {@link Customer}
 * 
 * @author HEKEDAR.
 *         Created Aug 13, 2018.
 */
public class CustomerTest {
	
	/**
	 * Check the Customer object is created with the given Customer Type and number of items 
	 */
	@Test
	public void shouldCreateCustomerObject(){
		//given
		CustomerType type = CustomerType.A;
		int numberOfItems = 5;
		
		//when
		Customer customer = new Customer(type, numberOfItems);
		
		//then
		assertEquals(type, customer.getType());
		assertEquals(numberOfItems, customer.getNumberOfItems().intValue());
	}
	
	/**
	 * This test should sort list of customers based on the the number of items
	 *
	 */
	@Test
	public void shouldSortCustomerBasedOnNoOfItems(){
		//given
		List<Customer> listOfCustomer = CustomerTestDO.getCustomerListWithDifferentNumberOfItems();

		//when
		Collections.sort(listOfCustomer, Customer.sortByItemNumbersOrType);
		
		//then
		verifySortCustomers(listOfCustomer);
	}
	
	/**
	 * Should return customer list sorted according to their type when number of items are equal<br>
	 * {@link CustomerType} A should be given preference before B
	 */
	@Test
	public void shouldSortCustomerBasedOnCustomerTypes(){
		//given
		List<Customer> listOfCustomer = CustomerTestDO.getCustomerListWithSameItemNumbers();

		//when
		Collections.sort(listOfCustomer, Customer.sortByItemNumbersOrType);
		
		//then
		assertEquals(CustomerType.A, listOfCustomer.get(0).getType());
		assertEquals(CustomerType.A, listOfCustomer.get(1).getType());
		assertEquals(CustomerType.B, listOfCustomer.get(2).getType());
	}
	

	/**
	 * When customer is served by expert the number of items should decrement with a call 
	 *
	 */
	@Test
	public void shouldBeServedByExpertCashier(){
		//given
		int numberOfItem = 5;
		Customer cust = new Customer(CustomerType.A, numberOfItem);
		
		//when
		int remainingItem = cust.itemsServedByExpert();
		
		//then
		assertEquals(--numberOfItem, remainingItem);
		
		//when
		remainingItem = cust.itemsServedByExpert();
		
		//then
		assertEquals(--numberOfItem, remainingItem);
	}
	
	/**
	 * Time taken by trainee cashier to serve a single item of customer is double the time taken by 
	 * expert cashier
	 */
	@Test
	public void shouldBeservedByTraineeCashier(){
		//given
		int numberOfItem = 5;
		Customer cust = new Customer(CustomerType.A, numberOfItem);
		//when
		int remainingItem = cust.itemsServedByTrainee();
		
		//then
		assertEquals(numberOfItem, remainingItem);
		
		//when
		remainingItem = cust.itemsServedByTrainee();
		
		//then
		assertEquals(--numberOfItem, remainingItem);
	}
	
	private void verifySortCustomers(List<Customer> sortedCustomerList){
		int noOfItems = sortedCustomerList.get(0).getNumberOfItems();
		sortedCustomerList.remove(0);
		for(Customer cust : sortedCustomerList){
			assertTrue(noOfItems <= cust.getNumberOfItems());
			noOfItems = cust.getNumberOfItems();
		}
	}
	
}
