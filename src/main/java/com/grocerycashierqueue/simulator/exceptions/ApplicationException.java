package com.grocerycashierqueue.simulator.exceptions;

/**
 * Class to handle application exception
 *
 * @author hekedar.
 *         Created Aug 12, 2018.
 */
public class ApplicationException extends RuntimeException{
	
	private final ErrorCode errorCode;
	
	/**
	 * Constructor<br>
	 * Create ApplicationException using {@link ErrorCode} 
	 *
	 * @param errorCode (required) System defined error code
	 */
	public ApplicationException(ErrorCode errorCode){
		this.errorCode = errorCode;
	}
	
	/**
	 * Constructor<br>
	 *
	 * @param message (required) User defined error message
	 * @param cause (required) Throwable
	 * @param errorCode (required) System defined error code
	 */
	public ApplicationException(String message, Throwable cause, ErrorCode errorCode){
		super(message, cause);
		this.errorCode = errorCode;
	}
	
	/**
	 * Constructor<br>
	 *
	 * @param message (required) User defined error message
	 * @param errorCode (required) System defined error code
	 */
	public ApplicationException(String message, ErrorCode errorCode){
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * Constructor<br>
	 *
	 * @param cause (required) Throwable
	 * @param errorCode (required) System defined error code	 */
	public ApplicationException(Throwable cause, ErrorCode errorCode){
		super(cause);
		this.errorCode = errorCode;
	}

	/**
	 * Get the error message defined at application for an {@link ErrorCode} 
	 *
	 * @return Error message
	 */
	public String getErrorMessage(){
		return this.errorCode.getErrorMsg();
	}
	
	@Override
	public String getMessage(){
		return this.errorCode.getErrorMsg();
	}
	
}
