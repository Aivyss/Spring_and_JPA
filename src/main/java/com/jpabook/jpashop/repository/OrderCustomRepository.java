package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.order.Order;
import com.jpabook.jpashop.dto.OrderSearchFilter;
import java.util.List;

public interface OrderCustomRepository {
	List<Order> searchOrders(OrderSearchFilter filter);
}
