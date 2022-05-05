package com.jpabook.jpashop.i18n;

import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@Slf4j
public class GlobalConfig implements WebMvcConfigurer {
	
	/**
	 * 스프링에서 구현된 메세지소스 객체를 빈으로 컨테이너에 추가한다.
	 * @return 메세지소스 빈
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages/map");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(10); // 리로드한 파일을 캐싱
		log.debug("[CUSTOM BEAN REGISTRY] messageSource");
		return messageSource;
	}
	
	/**
	 * 쿠키에 언어 정보를 넣어 두고 있고 쿠키에 있는 언어 정보를 가져오는 리졸버 빈을 컨테이너에 추가
	 * @return 로케일 리졸버 빈
	 */
	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setCookieName("langId"); // 가져올 쿠키의 키
		localeResolver.setDefaultLocale(Locale.ENGLISH); // 설정된 언어가 없을 시 제공되는 기본 언어 설정
		log.info("[CUSTOM BEAN REGISTRY] localeResolver");
		return localeResolver;
	}
	
	/**
	 * 로케일 변경 인터셉터를 컨테이너에 추가
	 * @return 로케일 변경 인터셉터
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("langId");
		log.info("[CUSTOM BEAN REGISTRY] localeChangeInterceptor");
		return interceptor;
	}
	
	/**
	 * 로케일 변경 인터셉터 빈을 인터셉터 레지스트리에 추가
	 * @param registry 인터셉터 레지스트리
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
		log.info("[PREPROCESS] add localeChangeInterceptor to interceptor registry");
	}
}
