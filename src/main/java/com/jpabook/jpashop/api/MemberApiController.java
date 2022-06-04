package com.jpabook.jpashop.api;

import com.jpabook.jpashop.domain.member.Member;
import com.jpabook.jpashop.dto.api.common.DtoWrapper;
import com.jpabook.jpashop.dto.api.member.CreateMemberResponse;
import com.jpabook.jpashop.dto.api.member.MemberCreationReq;
import com.jpabook.jpashop.dto.api.member.MemberInfoRes;
import com.jpabook.jpashop.dto.api.member.MemberUpdateReq;
import com.jpabook.jpashop.mvc.member.MemberService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberApiController {
	
	private final MemberService memberService;
	
	@PostMapping("")
	public CreateMemberResponse saveMemberV1(@RequestBody @Valid MemberCreationReq body) {
		final Member member = body.create();
		final Long id = memberService.signUp(member);
		
		final CreateMemberResponse response = new CreateMemberResponse();
		response.setId(id);
		
		return response;
	}
	
	@PutMapping("/{memberId}")
	public boolean updateMemberV1(
		@PathVariable long memberId,
		@RequestBody @Valid MemberUpdateReq body
	) {
		memberService.updateMember(memberId, body.getName(), body.getAddress());
		
		return true;
	}

	@GetMapping("")
	public DtoWrapper<List<MemberInfoRes>> getMemberInfoV1() {
		final List<Member> allMembers = memberService.findAllMembers();
		
		return new DtoWrapper<>(allMembers.stream().map(MemberInfoRes::from).collect(Collectors.toList()));
	}
	
}
