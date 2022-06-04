package com.jpabook.jpashop.mvc.member;

import com.jpabook.jpashop.domain.member.Member;
import com.jpabook.jpashop.dto.MemberForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@Api(value = "Member Controller V1")
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	@GetMapping("")
	@ApiOperation(
		httpMethod = "GET",
		value = "멤버 리스트 페이지 렌더링",
		notes = "렌더링 할 뷰 템플릿 매핑",
		tags=  {"render"}
	)
	public String viewMemberList(Model model) {
		final List<Member> memberEntities = memberService.findAllMembers();
		final List<MemberForm> members = memberEntities.stream()
			.map(MemberForm::memberToForm).collect(Collectors.toList());
		
		model.addAttribute("members", members);
		
		return "members/member-list";
	}
	
	@ApiOperation(
		httpMethod = "GET",
		value = "멤버 들록폼 페이지 렌더링",
		notes = "렌더링 할 뷰 템플릿 매핑",
		tags= {"render"}
	)
	@GetMapping("/new")
	public String viewSignupForm(Model model) {
		// * give form
		model.addAttribute("memberForm", new MemberForm());
		
		// * give view
		return "members/signup-form";
	}
	
	// An Errors/BindingResult argument is expected to be declared immediately after the model attribute, the @RequestBody or the @RequestPart arguments to which they apply: public java.lang.String
	// 골때린다 템플릿 내 폼객체명이 클래스명이랑 동일하게 해야 에러 바인딩이 된다...
	@PostMapping("/new")
	@ApiOperation(
		httpMethod = "POST",
		value = "멤버 등록 비즈니스 로직수행 메소드",
		notes = "멤버 등록 비즈니스 로직 수행 후 성공시 메인 페이리 렌더링으로 리다이렉트",
		tags= {"business_save", "Member_entity"}
	)
 	public String signup(@Valid MemberForm signupForm, BindingResult result) {
		// * check validation
		if (result.hasErrors()) {
			return "members/signup-form";
		}
		
		// * DTO -> Entity & persistOrMerge
		final Member member = MemberForm.formToMember(signupForm);
		memberService.signUp(member);
		
		// * redirect
		return "redirect:/";
	}
}
