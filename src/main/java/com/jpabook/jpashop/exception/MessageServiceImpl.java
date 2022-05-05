package com.jpabook.jpashop.exception;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
	private final MessageSource messageSource;
	
	@Override
	public String convertMessage(String messageId, String... args) {
		final Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(messageId, args, locale);
	}
}
