package com.jpabook.jpashop.mvc.order;

import com.jpabook.jpashop.domain.order.Order;
import com.jpabook.jpashop.dto.OrderSearchFilter;
import java.util.List;

public interface OrderService {
	Long createOrder(Long memberId, Long itemId, int count);
	void cancelOrder(Long orderId);
	List<Order> searchOrders(OrderSearchFilter filter);
}
