package com.grocerycashierqueue.simulator.buss;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.grocerycashierqueue.simulator.actors.Customer;
import com.grocerycashierqueue.simulator.actors.CustomerType;
import com.grocerycashierqueue.simulator.exceptions.ApplicationException;
import com.grocerycashierqueue.simulator.exceptions.ErrorCode;

/**
 * Helper class to read, validate and return the output from the input file.
 * The class returns value for number of registers and the list of Customers entering<br>
 * at a specific time along with number of Items 
 *
 * @author hekedar.
 *         Created Aug 12, 2018.
 */
public class InputFileDataReaderHelper {

	/**
	 * Space regex is used to split the data from file
	 */
	private static final String SPACE_REGEX = "\\s+";
	
	/**
	 * This are the magic numbers refactored as constant so the variable so the developer can easily understand a digit/index
	 * stands for what value<br> 
	 */
	private static final int CUSTOMER_TYPE_IDX = 0;
	private static final int TIME_OF_ENTRY_IDX = 1;
	private static final int NO_OF_ITEM_IDX = 2;
	
	/**
	 * Indicates number of data to get from line
	 */
	private static final int DATA_PER_LINE = 3;
	private int numOfRegisters = 0;
	
	/**
	 * Contains the list of customer mapped by the time of their entry in queue.
	 */
	private Map<Integer, List<Customer>> timeAndCustomerMapping = new HashMap<>();
	
	
	/**
	 * Read data from the input file and validate the inputs
	 *
	 * @param filePath
	 * @throws ApplicationException 
	 */
	public void readData(String filePath){
		checkFile(filePath);
		try(FileReader fReader = new FileReader(filePath);
				BufferedReader br = new BufferedReader(fReader);){
			
			String readLine = br.readLine();
			if(readLine == null){
				throw new ApplicationException(ErrorCode.EMPTY_FILE_EXCEPTION);
			}
			setNumOfRegisters(readLine);
			while((readLine = br.readLine()) != null){
				arrangeCustomerAccToTimeOfEntry(readLine);
			}
		} catch (FileNotFoundException e) {
			throw new ApplicationException(e, ErrorCode.FILE_NOT_FOUND_EXCEPTION);
		} catch (NumberFormatException e) {
			throw new ApplicationException(e, ErrorCode.NUMBER_FORMAT_EXCEPTION);
		} catch (IOException e) {
			throw new ApplicationException(e, ErrorCode.UNEXPECTED_EXCEPTION);
		}
	}

	/**
	 * Returns the value of the field called 'numOfRegisters'.
	 * @return Returns the numOfRegisters.
	 */
	public int getNumOfRegisters() {
		return this.numOfRegisters;
	}


	/**
	 * Returns the value of the field called 'timeAndCustomerMapping'.
	 * @return Returns the timeAndCustomerMapping.
	 */
	public Map<Integer, List<Customer>> getTimeAndCustomerMapping() {
		return this.timeAndCustomerMapping;
	}
	
	/**
	 * Check if the input file path is valid or not
	 *
	 * @param filePath
	 */
	private void checkFile(String filePath) {
		if(filePath == null || filePath.isEmpty()){
			throw new ApplicationException(ErrorCode.INVALID_FILE_EXCEPTION);
		}
	}
	
	/**
	 * @param numOfRegisters The numOfRegisters to set.
	 */
	private void setNumOfRegisters(String numOfRegisters) {
		this.numOfRegisters = Integer.parseInt(numOfRegisters);
	}

	/**
	 * Arranges and set the list of customer according to the time of their arrival in queue. 
	 *
	 * @param lineData
	 * @throws NumberFormatException
	 */
	private void arrangeCustomerAccToTimeOfEntry(String lineData) throws NumberFormatException{
		String[] dataArr = lineData.split(SPACE_REGEX);
		if(dataArr.length != DATA_PER_LINE){
			throw new ApplicationException(ErrorCode.INVALID_DATA_IN_FILE_EXCEPTION);
		}
		
		int timeOfEntry = Integer.parseInt(dataArr[TIME_OF_ENTRY_IDX]);
		if(this.timeAndCustomerMapping.containsKey(timeOfEntry)){
			this.timeAndCustomerMapping.get(timeOfEntry).add(getCustomerObject(dataArr));
		} else {
			Customer customer = getCustomerObject(dataArr);
			List<Customer> customerList = new ArrayList<>(Arrays.asList(customer)); 
			this.timeAndCustomerMapping.put(timeOfEntry, customerList);
		}
	}
	
	private Customer getCustomerObject(String[] data){
		return new Customer(CustomerType.valueOf(data[CUSTOMER_TYPE_IDX]), Integer.parseInt(data[NO_OF_ITEM_IDX]));
	}
	
}
