package com.grocerycashierqueue.simulator.actors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.grocerycashierqueue.simulator.dataobjects.RegisterTestDO;

/**
 * Unit test case for {@link Register}
 *
 * @author HEKEDAR.
 *         Created Aug 13, 2018.
 */
public class RegisterTest {
	/**
	 * Private constructor 
	 */
	public RegisterTest() {
		
	}
	
	/**
	 * Should create Register object with register index and cashier type.
	 * Also a new Customer queue should be created
	 *
	 */
	@Test
	public void shouldCreateRegisterObject(){
		//given
		int registerIndex = 1;
		boolean isCashierTrainee = false;
		
		//when
		Register register = new Register(registerIndex, isCashierTrainee);
		
		//then
		assertEquals(registerIndex, register.getIndex());
		assertEquals(isCashierTrainee, register.isCashierTrainee());
		Assert.assertNotNull(register.getCustomerQueue());
	}
	
	/**
	 * Should sort list of register based on their index in ascending
	 *
	 */
	@Test
	public void shouldSortListOfRegiterByIndex(){
		//given
		List<Register> registerList = RegisterTestDO.getListOfRegisters();

		//when
		Collections.sort(registerList, Register.sortByRegisterIndex);
		
		//then
		verifyRegistersAreSortedByIndex(registerList);
	}
	
	/**
	 * Should sort list of register based on their customer queue size in ascending order
	 * @throws IllegalAccessException 
	 *
	 */
	@Test
	public void shouldSortListOfRegisterByCustomerQueueSize() throws IllegalAccessException{
		//given
		List<Register> registerList = RegisterTestDO.getListOfRegisterWithCustomers();
		
		//when
		Collections.sort(registerList, Register.sortByCustomerQueueSize);
		
		//then
		verifyRegisterIsSortedByNumberOfCustomersInQueue(registerList);
	}

	/**
	 * Verify the customer is sorted based on the number of customers in queue in ascending order
	 *
	 * @param registerList (required)
	 */
	private void verifyRegisterIsSortedByNumberOfCustomersInQueue(List<Register> registerList) {
		Register register1 = registerList.get(0);
		Register register2 = registerList.get(1);
		assertTrue(register1.getCustomerQueue().size() < register2.getCustomerQueue().size());
	}

	/**
	 * Method verifies that the list of registers are sorted based on their index
	 * @param registerList
	 */
	private void verifyRegistersAreSortedByIndex(List<Register> registerList) {
		int index = registerList.get(0).getIndex();
		registerList.remove(0);
		for(Register register : registerList){
			assertTrue(index <= register.getIndex());
			index = register.getIndex();
		}
	}
}
