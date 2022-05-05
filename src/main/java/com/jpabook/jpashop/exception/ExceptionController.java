package com.jpabook.jpashop.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * <ul>
 *     <li> Exception handler </li>
 * </ul>
 */
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
	private final MessageService messageService;
	
	@ExceptionHandler(value = CommonError.class)
	public String handleException(CommonError e, RedirectAttributes model) {
		e.setErrorMessage(messageService);
		model.addFlashAttribute("errorMessage", e.getErrorMessage());
		model.addFlashAttribute("cause", e.getMessageId());
		
		return "redirect:/common/error";
	}
}
