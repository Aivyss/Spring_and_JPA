package com.jpabook.jpashop.exception;

import com.jpabook.jpashop.i18n.MessageService;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExceptionSupplierUtils {
	private Map<ExceptionCase, CommonError> simpleErrors = new HashMap<>();
	private Map<ExceptionCase, Supplier<CommonError>> simpleErrorSuppliers = new HashMap<>();
	private final MessageService messageService;
	
	@PostConstruct
	public void init() {
		simpleErrors.put(ExceptionCase.UNKNOWN_ERROR, getError(ExceptionCase.UNKNOWN_ERROR));
		simpleErrors.put(ExceptionCase.DUPLICATE_ROW, getError(ExceptionCase.DUPLICATE_ROW));
		simpleErrors.put(ExceptionCase.NOT_ENOUGH_STOCK, getError(ExceptionCase.NOT_ENOUGH_STOCK));
		simpleErrors.put(ExceptionCase.NOT_FOUND_DATA, getError(ExceptionCase.NOT_FOUND_DATA));
		
		simpleErrorSuppliers.put(ExceptionCase.UNKNOWN_ERROR, () -> simpleErrors.get(ExceptionCase.UNKNOWN_ERROR));
		simpleErrorSuppliers.put(ExceptionCase.DUPLICATE_ROW, () -> simpleErrors.get(ExceptionCase.DUPLICATE_ROW));
		simpleErrorSuppliers.put(ExceptionCase.NOT_ENOUGH_STOCK, () -> simpleErrors.get(ExceptionCase.NOT_ENOUGH_STOCK));
		simpleErrorSuppliers.put(ExceptionCase.NOT_FOUND_DATA, () -> simpleErrors.get(ExceptionCase.NOT_FOUND_DATA));
	}
	
	public CommonError getExceptionInstance(ExceptionCase exceptionCase) {
		CommonError commonError = simpleErrors.get(exceptionCase);
		if (commonError == null) commonError = simpleErrors.get(ExceptionCase.UNKNOWN_ERROR);
		
		return commonError;
	}
	
	public Supplier<CommonError> getExceptionSupplier(ExceptionCase exceptionCase) {
		Supplier<CommonError> commonErrorSupplier = simpleErrorSuppliers.get(exceptionCase);
		if (commonErrorSupplier == null) commonErrorSupplier = simpleErrorSuppliers.get(ExceptionCase.UNKNOWN_ERROR);
		
		return commonErrorSupplier;
	}
	
	private CommonError getError(ExceptionCase exceptionCase) {
		final CommonError error = new CommonError(exceptionCase);
		error.setErrorMessage(messageService);
		
		return error;
	}
}
