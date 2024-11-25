package com.himfirst.vidshare.exceptions;

public class ApiResponseException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8567459789085276140L;
	public ApiResponseException(String msg) {
		super(msg);
	}	
	public ApiResponseException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
}
