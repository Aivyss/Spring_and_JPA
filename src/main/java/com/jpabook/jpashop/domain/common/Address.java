package com.jpabook.jpashop.domain.common;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable // JPA entity의 내장타입으로서 사용. 타 entity 클래스에서 이 클래스의 필드들을 컬럼을 그대로 이용하겠다는 것.
@Getter @ToString
public class Address {
	private String city;
	private String street;
	private String zipCode;
	
	/**
	 * JPA는 reflection, proxy 기술을 활용하기 때문에 default constructor를 요구한다.
	 */
	protected Address () {} // 
	
	public Address(String city, String street, String zipCode) {
		this.city = city;
		this.street = street;
		this.zipCode = zipCode;
	}
}
