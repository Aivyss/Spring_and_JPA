package com.jpabook.jpashop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddressForm {
	private String city;
	private String street;
	private String zipCode;
}
