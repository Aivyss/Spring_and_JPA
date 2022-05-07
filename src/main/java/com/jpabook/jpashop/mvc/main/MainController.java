package com.jpabook.jpashop.mvc.main;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("/")
@Api(value = "Main Controller V1")
@Slf4j
public class MainController {
	@GetMapping("")
	@ApiOperation(
		httpMethod = "GET",
		value = "메인 페이지 렌더링",
		notes = "메인 페이지 템플레이트를 매핑하는 컨트롤러 메소드",
		tags = {"render"}
	)
	public String mainView(Model model) {
		return "main";
	}
	
	@GetMapping("common/error")
	@ApiOperation(
		httpMethod = "GET",
		value = "에러 페이지 렌더링",
		notes = "익셉션 컨트롤러에 의해 리다이렉트 되는 곳. 에러원인과 메세지가 다국어 처리 되어 페이지에 보여짐",
		tags = {"render"}
	)
	public String renderErrorPage() {
		return "common/error";
	}
}
