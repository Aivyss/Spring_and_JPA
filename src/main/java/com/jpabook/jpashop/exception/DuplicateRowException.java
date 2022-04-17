package com.jpabook.jpashop.exception;

@SuppressWarnings("unused")
public class DuplicateRowException extends RuntimeException {
	private final String errorCode;
	
	public DuplicateRowException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
}
