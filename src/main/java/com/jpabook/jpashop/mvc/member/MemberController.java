package com.jpabook.jpashop.mvc.member;

import com.jpabook.jpashop.domain.member.Member;
import com.jpabook.jpashop.dto.MemberForm;
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
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	@GetMapping("")
	public String viewMemberList(Model model) {
		final List<Member> memberEntities = memberService.findAllMembers();
		final List<MemberForm> members = memberEntities.stream()
			.map(MemberForm::memberToForm).collect(Collectors.toList());
		
		model.addAttribute("members", members);
		
		return "members/member-list";
	}
	
	@GetMapping("/new")
	public String createSignupForm(Model model) {
		// * give form
		model.addAttribute("memberForm", new MemberForm());
		
		// * give view
		return "members/signup-form";
	}
	
	// An Errors/BindingResult argument is expected to be declared immediately after the model attribute, the @RequestBody or the @RequestPart arguments to which they apply: public java.lang.String
	// 골때린다 템플릿 내 폼객체명이 클래스명이랑 동일하게 해야 에러 바인딩이 된다...
	@PostMapping("/new")
	public String signup(@Valid MemberForm signupForm, BindingResult result) {
		// * check validation
		if (result.hasErrors()) {
			return "members/signup-form";
		}
		
		// * DTO -> Entity & save
		final Member member = MemberForm.formToMember(signupForm);
		memberService.signUp(member);
		
		// * redirect
		return "redirect:/";
	}
}
