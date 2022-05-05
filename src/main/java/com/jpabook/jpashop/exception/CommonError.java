package com.jpabook.jpashop.exception;

import com.jpabook.jpashop.i18n.MessageService;
import com.jpabook.jpashop.interfaces.exceptions.JPAShopError;

public class CommonError extends RuntimeException implements JPAShopError {
	private final ExceptionCase exceptionCase;
	private String errorCode;
	private String messageId;
	private String errorMessage;
	private String[] args = new String[]{};
	
	public CommonError(ExceptionCase exceptionCase, String errorCode, String messageId, String... args) {
		super();
		this.exceptionCase = exceptionCase;
		this.errorCode = errorCode;
		this.messageId = messageId;
		this.args = args;
	}
	
	/**
	 * @param messageService 다국어 처리 비즈니스 로직 인스턴스
	 */
	@Override
	public void setErrorMessage(MessageService messageService) {
		this.errorMessage = messageService.convertMessage(messageId, args);
	}
	
	@Override
	public String getErrorCode() {
		return errorCode;
	}
	
	@Override
	public String getMessageId() {
		return messageId;
	}
	
	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
}
