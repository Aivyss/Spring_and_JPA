package com.jpabook.jpashop.exception;

public interface MessageService {
	String convertMessage(String messageId, String... args);
}
