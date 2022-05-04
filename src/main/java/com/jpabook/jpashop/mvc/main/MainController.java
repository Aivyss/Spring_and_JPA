package com.jpabook.jpashop.mvc.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
@Slf4j
public class MainController {
	@GetMapping("/")
	public String mainView(Model model) {
		model.addAttribute("data", "hello");
		log.info("give main view template");
		return "main";
	}
}
