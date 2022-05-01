package com.jpabook.jpashop.exception;

import com.jpabook.jpashop.interfaces.exceptions.JPAShopError;

@SuppressWarnings("unused")
public class DuplicateRowException extends RuntimeException implements JPAShopError {
	private final String errorCode;
	
	public DuplicateRowException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	@Override
	public String getErrorCode() {
		return errorCode;
	}
}