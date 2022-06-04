package com.jpabook.jpashop.dto.api.member;

import com.jpabook.jpashop.domain.member.Member;
import com.jpabook.jpashop.dto.AddressForm;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCreationReq {
	@NotEmpty
	private String name;
	@NotEmpty
	private String nickname;
	private AddressForm address;
	
	public Member create() {
		return Member.create(name, nickname, address.formToEntity());
	}
}
