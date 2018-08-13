package com.grocerycashierqueue.simulator.exceptions;

/**
 * TODO Put here a description of what this class does.
 *
 * @author HEKEDAR.
 *         Created Aug 12, 2018.
 */
public enum ErrorCode {
	
	/**
	 * Thrown when there is any unexpected error found.
	 */
	UNEXPECTED_EXCEPTION("Opps... There is an unexpected exception has occured."),
	
	/**
	 * Thrown when the provided file is empty
	 */
	EMPTY_FILE_EXCEPTION("File is Empty."),
	
	/**
	 * Thrown when data is the input file is invalid
	 */
	INVALID_DATA_IN_FILE_EXCEPTION("Data in a file is Invalid. Please provide valid data file."),
	
	/**
	 * Thrown when non-numeric values are found instead of numeric value
	 */
	NUMBER_FORMAT_EXCEPTION("Non numeric value found where there should be numeric value"),
	
	/**
	 * Thrown when file provided is not found
	 */
	FILE_NOT_FOUND_EXCEPTION("Data file not found"),
	
	/**
	 * Thrown when file provided is invalid
	 */
	INVALID_FILE_EXCEPTION("Invalid File. Please provide valid file."),
	
	/**
	 * Thrown when file name is not passed as an argument 
	 */
	FILE_NOT_PROVIDED_EXCEPTION("File not provided. Please provide file as an argument.");
	
	
	private String errorMsg;
	private ErrorCode(String msg){
		this.errorMsg = msg;
	}
	
	/**
	 * Get the error message defined at the application for an Error Code
	 * TODO Put here a description of what this method does.
	 *
	 * @return Error message defined in system for an error code
	 */
	public String getErrorMsg(){
		return this.errorMsg;
	}
}
