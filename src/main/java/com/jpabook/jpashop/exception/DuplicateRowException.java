package com.jpabook.jpashop.exception;

public class DuplicateRowException extends RuntimeException {
	private final String errorCode;
	
	public DuplicateRowException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
}
