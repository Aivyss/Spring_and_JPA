package com.jpabook.jpashop.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class MainController {
	@GetMapping("/")
	public String mainView(Model model) {
		model.addAttribute("data", "hello");
		
		return "main";
	}
}
