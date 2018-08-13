package com.grocerycashierqueue.simulator.buss;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.grocerycashierqueue.simulator.actors.Customer;
import com.grocerycashierqueue.simulator.actors.CustomerType;
import com.grocerycashierqueue.simulator.actors.Register;
import com.grocerycashierqueue.simulator.dataobjects.CustomerTestDO;
import com.grocerycashierqueue.simulator.dataobjects.RegisterTestDO;

/**
 * Unit test for RegisterBuss
 *
 * @author HEKEDAR.
 *         Created Aug 13, 2018.
 */
public class RegisterBussTest {
	
	/**
	 * Should create list of {@link Register} for given number of registers with trainee cashier register at the end
	 * and should return false when check for register serving customer as they are empty
	 */
	@Test
	public void testRegisterCreation(){
		//given
		int noOfRegisters = 2;
		
		//when
		RegisterBuss registerBuss = new RegisterBuss(noOfRegisters);
		
		//then
		assertEquals(noOfRegisters, registerBuss.getRegisterList().size());
		assertFalse(registerBuss.getRegisterList().get(0).isCashierTrainee());
		assertTrue(registerBuss.getRegisterList().get(1).isCashierTrainee());
		assertFalse(registerBuss.isAnyCustomerIsBeingServed());
	}
	/**
	 * Should return true if any of the registers is serving customer
	 * @throws IllegalAccessException 
	 */
	@Test
	public void shouldCheckIfAnyRegisterIsServingCustomer() throws IllegalAccessException{
		//given
		RegisterBuss registerBuss = RegisterTestDO.getRegisterBussWithCustomers();
		
		//when
		boolean isServing = registerBuss.isAnyCustomerIsBeingServed();
		
		//then
		assertTrue(isServing);
	}

	/**
	 * Should start serving customer 
	 */
	@Test
	public void shouldServeCustomerBasedOnITemAndCustomerType(){
		//given
		int noOfRegisters = 2;
		RegisterBuss registerBuss = new RegisterBuss(noOfRegisters);
		List<Customer> customerList = CustomerTestDO.getCustomerListArrivingAtSameTime();
		
		//when
		registerBuss.startServicingCustomers(customerList);
		
		//then
		verifyCustomerPlacementInRegisterWhileServing(registerBuss);
	}
	
	/**
	 * Should serve next customer even if the queue is not empty 
	 * The serving should be done based on Customer Type and number of items with
	 * last customer in queue 
	 *
	 * @throws IllegalAccessException
	 */
	@Test
	public void shouldServeCustomerArrivingAtGivenTime() throws IllegalAccessException{
		//given
		RegisterBuss registerBuss = RegisterTestDO.getRegisterBussWithCustomers();
		List<Customer> customerList = CustomerTestDO.getCustomerListArrivingAtSameTime();
		
		//when
		registerBuss.startServicingCustomers(customerList);
		
		//when
		List<Register> registerList = registerBuss.getRegisterList();
		Register register1 = registerList.get(0);
		Register register2 = registerList.get(1);
		assertEquals(3, register1.getCustomerQueue().size());
		assertEquals(3, register2.getCustomerQueue().size());
		
		assertEquals(CustomerType.A, register1.getCustomerQueue().poll().getType());
		assertEquals(CustomerType.B, register1.getCustomerQueue().poll().getType());
		
		assertEquals(CustomerType.A, register2.getCustomerQueue().poll().getType());
	}
	
	/**
	 * An expert cashier should server customer and decrement number of items in first call
	 * And also remove customer from list if all the items are served.
	 * @throws IllegalAccessException 
	 *
	 */
	@Test
	public void shouldServeCustomerByExpertCashier() throws IllegalAccessException{
		//given
		RegisterBuss registerBuss = RegisterTestDO.getRegisterBussWithCustomers();
		Register register = registerBuss.getRegisterList().get(0);
		int totalCustomerInQueue = register.getCustomerQueue().size();
		int actualNumberOfItems = register.getCustomerQueue().peek().getNumberOfItems();
		
		//when
		registerBuss.serveCustomer(register); //1st Register is expert
		
		//then
		int numberOfItemsAfterFirstServe = register.getCustomerQueue().peek().getNumberOfItems();
		assertEquals(actualNumberOfItems-1, numberOfItemsAfterFirstServe);
		
		//when
		registerBuss.serveCustomer(register); //next item served
		
		//then
		assertEquals(totalCustomerInQueue-1, register.getCustomerQueue().size());
	}
	
	/**
	 * Time taken by trainee cashier should be double the time taken by expert cashier
	 * and should reduce the customer in queue after all the items are served
	 * @throws IllegalAccessException
	 */
	@Test
	public void shouldServeCustomerByTraineeCashier() throws IllegalAccessException{
		//given
		RegisterBuss registerBuss = RegisterTestDO.getRegisterBussWithCustomers();
		Register register = registerBuss.getRegisterList().get(1);
		int totalCustomerInQueue = register.getCustomerQueue().size();
		int actualNumberOfItems = register.getCustomerQueue().peek().getNumberOfItems();
		
		//when
		registerBuss.serveCustomer(register); //1st Register is expert
		
		//then
		int numberOfItemsAfterFirstServe = register.getCustomerQueue().peek().getNumberOfItems();
		assertEquals(actualNumberOfItems, numberOfItemsAfterFirstServe);
		
		//when
		registerBuss.serveCustomer(register); //next item served
		
		//then
		assertEquals(totalCustomerInQueue-1, register.getCustomerQueue().size());
	}
	
	private void verifyCustomerPlacementInRegisterWhileServing(RegisterBuss registerBuss) {
		List<Register> registerList = registerBuss.getRegisterList();
		Register register1 = registerList.get(0);
		Register register2 = registerList.get(1);
		assertEquals(2, register1.getCustomerQueue().size());
		assertEquals(1, register2.getCustomerQueue().size());
		
		assertEquals(CustomerType.A, register1.getCustomerQueue().poll().getType());
		assertEquals(CustomerType.A, register1.getCustomerQueue().poll().getType());
		
		assertEquals(CustomerType.B, register2.getCustomerQueue().poll().getType());
	}
}
