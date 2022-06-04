package com.jpabook.jpashop.dto;

import com.jpabook.jpashop.domain.common.Address;
import com.jpabook.jpashop.domain.member.Member;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {
	@NotEmpty(message = "{FORM_INSTANCE.MEMBER_FORM.NOT_EMPTY.NICKNAME}")
	private String nickname;
	@NotEmpty(message = "{FORM_INSTANCE.MEMBER_FORM.NOT_EMPTY.NAME}")
	private String name;
	private AddressForm address;
	private Long id;
	
	public static Member formToMember(MemberForm form) {
		final AddressForm addressForm = form.getAddress();
		return Member.create(form.getName(), form.getNickname(), addressForm.formToEntity());
	}
	
	public static MemberForm memberToForm(Member member) {
		// * get variables from entities
		final String nickname = member.getNickname();
		final String name = member.getName();
		final Address address = member.getAddress();
		final Long id = member.getId();
		
		// * make forms
		final MemberForm memberForm = new MemberForm();
		final AddressForm addressForm = AddressForm.entityToForm(address);
		memberForm.setNickname(nickname);
		memberForm.setName(name);
		memberForm.setAddress(addressForm);
		memberForm.setId(id);
		
		return memberForm;
	}
}

