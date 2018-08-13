package com.grocerycashierqueue.simulator.buss;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.grocerycashierqueue.simulator.actors.Customer;
import com.grocerycashierqueue.simulator.exceptions.ApplicationException;
import com.grocerycashierqueue.simulator.exceptions.ErrorCode;

/**
 * Unit test for input file reader helper
 *
 * @author HEKEDAR.
 *         Created Aug 13, 2018.
 */
public class InputFileDataReaderHelperTest {
	
	private InputFileDataReaderHelper inputFileDataReader;
	
	private static String INPUT_DATAFILE_LOCATION;
	
	/**
	 * Expected exception catcher
	 */
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	/**
	 * Initialize class variables; 
	 */
	@Before
	public void setUp(){
		INPUT_DATAFILE_LOCATION = this.getClass().getProtectionDomain().getCodeSource()
								.getLocation().toString().replace("file:/", "");
		this.inputFileDataReader = new InputFileDataReaderHelper();
	}
	
	/**
	 * Should throw exception when file name not provided
	 */
	@Test
	public void shouldThrowExceptionWhenNoFileIsPassedAsParameter(){
		//given
		this.thrown.expect(ApplicationException.class);
		this.thrown.expectMessage(ErrorCode.INVALID_FILE_EXCEPTION.getErrorMsg());
		
		//when
		this.inputFileDataReader.readData(null);
	}
	
	/**
	 * Check the file reader is throwing error message for empty file
	 *
	 */
	@Test
	public void shouldThrowExceptionForBlankFile(){
		//given
		String blankFile = INPUT_DATAFILE_LOCATION + "errorFiles/blankFile.txt";
		this.thrown.expect(ApplicationException.class);
		this.thrown.expectMessage(ErrorCode.EMPTY_FILE_EXCEPTION.getErrorMsg());
		
		//when
		this.inputFileDataReader.readData(blankFile);
	}
	
	/**
	 * Should throw exception with message file not found
	 *
	 */
	@Test
	public void shouldThrowExceptionWhenFileNotFound(){
		//given
		String inputFile = INPUT_DATAFILE_LOCATION + "errorFiles/ABC.txt";
		this.thrown.expect(ApplicationException.class);
		this.thrown.expectMessage(ErrorCode.FILE_NOT_FOUND_EXCEPTION.getErrorMsg());
		
		//when
		this.inputFileDataReader.readData(inputFile);
	}
	
	/**
	 * Should throw exception with message Invalid data exception when invalid data or missing data
	 *
	 */
	@Test
	public void shouldThrowExceptionForInvalidOrMissingData(){
		//given
		String inputFile = INPUT_DATAFILE_LOCATION + "errorFiles/invalidDataFile.txt";
		this.thrown.expect(ApplicationException.class);
		this.thrown.expectMessage(ErrorCode.INVALID_DATA_IN_FILE_EXCEPTION.getErrorMsg());
		
		//when
		this.inputFileDataReader.readData(inputFile);
	}
	
	/**
	 * Should throw exception with message Number format exception when non-numeric data found instead of numeric
	 *
	 */
	@Test
	public void shouldThrowExceptionWhenNonNumericDataFound(){
		//given
		String inputFile = INPUT_DATAFILE_LOCATION + "errorFiles/fileWithNonNumericData.txt";
		this.thrown.expect(ApplicationException.class);
		this.thrown.expectMessage(ErrorCode.NUMBER_FORMAT_EXCEPTION.getErrorMsg());
		
		//when
		this.inputFileDataReader.readData(inputFile);
	}
	
	
	/**
	 * 
	 * Should read the data from input data file and provide total number of registers and list of customer mapped
	 * with time they enter the queue
	 *
	 */
	@Test
	public void shouldReadFromFile(){
		//given
		String inputFile = INPUT_DATAFILE_LOCATION + "examples/example3.txt";
		
		//when
		this.inputFileDataReader.readData(inputFile);
		
		//then
		Assert.assertEquals(2, this.inputFileDataReader.getNumOfRegisters());
		Map<Integer, List<Customer>> timeAndCustomerMapping = this.inputFileDataReader.getTimeAndCustomerMapping();
		Assert.assertEquals(3, timeAndCustomerMapping.size());
		Assert.assertEquals(2, timeAndCustomerMapping.get(1).size());
		Assert.assertEquals(1, timeAndCustomerMapping.get(2).size());
		Assert.assertEquals(1, timeAndCustomerMapping.get(3).size());
	}
}
