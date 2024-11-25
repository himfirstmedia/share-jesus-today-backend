package com.himfirst.vidshare.exceptions;

import java.util.Date;
public class ErrorDetails {
	
	private int code;
    private Date timeStamp;    
    private String message;    
    private String details;
    
    
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public ErrorDetails(int code, Date timeStamp, String message, String details) {
		super();
		this.code=code;
		this.timeStamp = timeStamp;
		this.message = message;
		this.details = details;
	}
    
    
    
    
}
