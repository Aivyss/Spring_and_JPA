package com.jpabook.jpashop.dto;

import com.jpabook.jpashop.domain.common.Address;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddressForm {
	private String city;
	private String street;
	private String zipCode;
	
	public Address formToEntity() {
		return new Address(getCity(), getStreet(), getZipCode());
	}
	
	public static AddressForm entityToForm(Address address) {
		final AddressForm addressForm = new AddressForm();
		addressForm.setCity(address.getCity());
		addressForm.setStreet(address.getStreet());
		addressForm.setZipCode(address.getZipCode());
		
		return addressForm;
	}
}
