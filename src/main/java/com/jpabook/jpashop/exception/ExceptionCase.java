package com.jpabook.jpashop.exception;

public enum ExceptionCase {
	UNKNOWN_ERROR("000", "EXCEPTION.UNKNOWN_ERROR"),
	DUPLICATE_ROW("001", "EXCEPTION.DUPLICATE_ROW_MEMBER"),
	NOT_ENOUGH_STOCK("002", "EXCEPTION.NOT_ENOUGH_STOCK"),
	NOT_FOUND_DATA("003", "EXCEPTION.NOT_FOUND_DATA");
	
	public final String errorCode;
	public final String messageId;
	
	ExceptionCase(String errorCode, String messageId) {
		this.errorCode = errorCode;
		this.messageId = messageId;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public String getMessageId() {
		return messageId;
	}
}
