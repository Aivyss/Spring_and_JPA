package com.jpabook.jpashop.aop;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Component
@Aspect
public class LoggingAspect {
	
	@Around("@annotation(com.jpabook.jpashop.annotation.aop.LogProcessTime)")
	public Object logProcessTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		final long begin = System.currentTimeMillis();
		
		final Object proceed = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
		final Signature signature = proceedingJoinPoint.getSignature();
		final Method method = ((MethodSignature) signature).getMethod();
		final RequestMapping annotation = method.getAnnotation(RequestMapping.class);
		final String[] value = annotation.value();
		System.out.println(value);
		final long end = System.currentTimeMillis();
		log.info("process time: " + (end - begin));
		
		return proceed;
	}
}
