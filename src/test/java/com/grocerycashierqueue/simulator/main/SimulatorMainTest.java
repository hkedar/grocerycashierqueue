package com.grocerycashierqueue.simulator.main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.grocerycashierqueue.simulator.exceptions.ErrorCode;

/**
 *
 * @author HEKEDAR.
 *         Created Aug 12, 2018.
 */
public class SimulatorMainTest {

	private static final Map<String, Integer> TEST_RESOURCE_TIMEOUTPUT = new HashMap<>();
	
	private static String INPUT_DATAFILE_LOCATION;
	
	/**
	 * Initial method to create list of data and output for running test case
	 *
	 */
	@Before
	public void setUp(){
		INPUT_DATAFILE_LOCATION = this.getClass().getProtectionDomain()
				.getCodeSource().getLocation().toString().replace("file:/", "")+"examples/";
		TEST_RESOURCE_TIMEOUTPUT.put("example1.txt", 7);
		TEST_RESOURCE_TIMEOUTPUT.put("example2.txt", 13);
		TEST_RESOURCE_TIMEOUTPUT.put("example3.txt", 6);
		TEST_RESOURCE_TIMEOUTPUT.put("example4.txt", 9);
		TEST_RESOURCE_TIMEOUTPUT.put("example5.txt", 11);
	}
	
	/**
	 * Execute the simulator test with the given input and assert the output
	 *
	 * @throws IOException
	 */
	@Test
	public void testGroceryCashierQueueSimulator() throws IOException{
		SimulatorMain simulator = new SimulatorMain();
		for(String fileName : TEST_RESOURCE_TIMEOUTPUT.keySet()){
			int time = simulator.runSimulator(INPUT_DATAFILE_LOCATION+fileName);
			Assert.assertEquals(TEST_RESOURCE_TIMEOUTPUT.get(fileName).intValue(), time);
		}
	}
	
	/**
	 * The simulator should return the output in specific format
	 *
	 * @throws IOException
	 */
	@Test
	public void testGroceryCashierPrintingDesiredOutput() throws IOException{
		 try{
			 for(String fileName : TEST_RESOURCE_TIMEOUTPUT.keySet()){
				 ByteArrayOutputStream outContent = new ByteArrayOutputStream();
				 System.setOut(new PrintStream(outContent));
				 String[] arg = {INPUT_DATAFILE_LOCATION+fileName};
				 SimulatorMain.main(arg);
				 String acceptedOutput = "Finished at : t=" + TEST_RESOURCE_TIMEOUTPUT.get(fileName) + " minutes";
				 Assert.assertEquals(acceptedOutput, outContent.toString());
				 outContent.close();
			 }
		 } catch (IOException e) {
			throw e;
		}
	}
	
	/**
	 * Should print an exception when file is not passed as an argument
	 *
	 * @throws IOException
	 */
	@Test()
	public void shouldPrintExceptionMessageWhenNoFileIsPassed() throws IOException{
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		SimulatorMain.main(null);
		String acceptedOutput = "Error: " + ErrorCode.FILE_NOT_PROVIDED_EXCEPTION.getErrorMsg();
		Assert.assertEquals(acceptedOutput, outContent.toString());
		outContent.close();
	}
}
