package com.jpabook.jpashop.dto;

import com.jpabook.jpashop.domain.order.Order;
import com.jpabook.jpashop.domain.order.OrderItem;
import com.jpabook.jpashop.domain.order.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderListForm {
	private long orderId;
	private String memberName;
	private String itemName;
	private int orderPrice;
	private int count;
	private String orderStatus;
	private LocalDateTime orderDate;
	
	public OrderListForm(long orderId, String memberName, String itemName, int orderPrice,
		int count, OrderStatus orderStatus, LocalDateTime orderDate) {
		this.orderId = orderId;
		this.memberName = memberName;
		this.itemName = itemName;
		this.orderPrice = orderPrice;
		this.count = count;
		this.orderStatus = orderStatus.name();
		this.orderDate = orderDate;
	}
	
	public static List<OrderListForm> from(Order order) {
		final List<OrderItem> orderItems = order.getOrderItems();
		return orderItems.stream().map(orderItem -> new OrderListForm(
			order.getId(),
			order.getMember().getName(),
			orderItem.getItem().getName(),
			orderItem.getOrderPrice(),
			orderItem.getCount(),
			order.getStatus(),
			order.getOrderDate()
		)).collect(Collectors.toList());
	}
	
	public static OrderListForm from(OrderItem orderItem) {
		final Order order = orderItem.getOrder();
		return new OrderListForm(
			order.getId(),
			order.getMember().getName(),
			orderItem.getItem().getName(),
			orderItem.getOrderPrice(),
			orderItem.getCount(),
			order.getStatus(),
			order.getOrderDate()
		);
	}
}
