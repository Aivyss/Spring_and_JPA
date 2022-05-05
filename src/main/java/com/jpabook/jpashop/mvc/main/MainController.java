package com.jpabook.jpashop.mvc.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("/")
@Slf4j
public class MainController {
	@GetMapping("")
	public String mainView(Model model) {
		return "main";
	}
	
	@GetMapping("common/error")
	public String renderErrorPage() {
		return "common/error";
	}
}
