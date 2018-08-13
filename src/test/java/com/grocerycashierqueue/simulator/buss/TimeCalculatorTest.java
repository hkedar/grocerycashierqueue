package com.grocerycashierqueue.simulator.buss;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.grocerycashierqueue.simulator.actors.Customer;


/**
 * TODO Put here a description of what this class does.
 *
 * @author HEKEDAR.
 *         Created Aug 13, 2018.
 */
public class TimeCalculatorTest {
	
	private Map<Integer, List<Customer>> customerQueueWithTimeMapping;
	private int numOfRegister;
	private final int accpectedTime = 11;
	
	/**
	 * Initial method to create list of data and output for running test case
	 *
	 */
	@Before
	public void setUp(){
		String fileLocation = this.getClass().getProtectionDomain()
				.getCodeSource().getLocation().toString().replace("file:/", "")+"examples/example5.txt";
		
		InputFileDataReaderHelper fileReader = new InputFileDataReaderHelper();
		fileReader.readData(fileLocation);
		this.numOfRegister = fileReader.getNumOfRegisters();
		this.customerQueueWithTimeMapping = fileReader.getTimeAndCustomerMapping();
	}
	
	/**
	 * Should calculate total time to  
	 */
	@Test
	public void shouldCalculateTotalTimeForServingAllTheCustomers(){
		//given
		TimeCalculator timeCalc = new TimeCalculator();
		RegisterBuss regBuss = new RegisterBuss(this.numOfRegister);
		
		//when
		int time = timeCalc.calculate(regBuss, this.customerQueueWithTimeMapping);
		
		//then
		Assert.assertEquals(this.accpectedTime, time);
	}
}
