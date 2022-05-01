package com.jpabook.jpashop.exception;

import com.jpabook.jpashop.interfaces.exceptions.JPAShopError;

public class NotEnoughStockException extends RuntimeException implements JPAShopError {
	private final String errorCode;
	
	public NotEnoughStockException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	
	@Override
	public String getErrorCode() {
		return errorCode;
	}
}
