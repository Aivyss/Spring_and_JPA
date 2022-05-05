package com.jpabook.jpashop.i18n;

public interface MessageService {
	String convertMessage(String messageId, String... args);
}
