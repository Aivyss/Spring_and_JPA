package com.jpabook.jpashop.dto;

import com.jpabook.jpashop.domain.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearchFilter {
	private String memberName;
	private OrderStatus orderStatus;
}
