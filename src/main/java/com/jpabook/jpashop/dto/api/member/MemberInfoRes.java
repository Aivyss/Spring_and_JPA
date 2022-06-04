package com.jpabook.jpashop.dto.api.member;

import com.jpabook.jpashop.domain.common.Address;
import com.jpabook.jpashop.domain.member.Member;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfoRes {
	private String name;
	private String nickname;
	private Address address;
	
	private MemberInfoRes(String name, String nickname, Address address) {
		this.name = name;
		this.nickname = nickname;
		this.address = address;
	}
	
	public static MemberInfoRes from(Member member) {
		return new MemberInfoRes(member.getName(), member.getNickname(), member.getAddress());
	}
}
