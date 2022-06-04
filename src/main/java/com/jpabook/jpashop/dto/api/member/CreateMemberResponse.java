package com.jpabook.jpashop.dto.api.member;

import com.jpabook.jpashop.domain.member.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateMemberResponse {
	private Long id;
	
	public CreateMemberResponse(Long id) {
		this.id = id;
	}
	
	public static CreateMemberResponse from(Member member) {
		return new CreateMemberResponse(member.getId());
	}
}
