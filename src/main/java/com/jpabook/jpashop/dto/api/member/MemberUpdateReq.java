package com.jpabook.jpashop.dto.api.member;

import com.jpabook.jpashop.domain.common.Address;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberUpdateReq {
	@NotEmpty
	private String name;
	@NotNull
	private Address address;
}
