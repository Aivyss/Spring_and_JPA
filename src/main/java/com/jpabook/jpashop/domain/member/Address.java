package com.jpabook.jpashop.domain.member;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable // JPA entity의 내장타입으로서 사용. 타 entity 클래스에서 이 클래스의 필드들을 컬럼을 그대로 이용하겠다는 것.
@Getter @Setter @ToString
public class Address {
	private String city;
	private String street;
	private String zipCode;
}
