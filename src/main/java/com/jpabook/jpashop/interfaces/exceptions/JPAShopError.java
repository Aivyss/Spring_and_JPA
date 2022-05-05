package com.jpabook.jpashop.interfaces.exceptions;

import com.jpabook.jpashop.exception.MessageService;

public interface JPAShopError {
	
	/**
	 * 설계상 결정된 에러코드로 개발자용
	 * @return 에러코드
	 */
	String getErrorCode();
	
	/**
	 * 다국어 처리를 위해 매핑되는 키값을 주는 메소드 스펙
	 * @return 다국어 코드
	 */
	String getMessageId();
	
	/**
	 * 다국어 처리가 되어 변환된 메세지
	 * @return 다국어 처리된 에러메세지
	 */
	String getErrorMessage();
	
	/**
	 * 다국어 처리된 메세지를 예외객체에 넣어주는 메소드
	 * @param messageService 다국어처리 비즈니스로직 객체
	 */
	void setErrorMessage(MessageService messageService);
}
