package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.order.OrderItem;
import com.jpabook.jpashop.dto.OrderListForm;
import com.jpabook.jpashop.dto.OrderSearchFilter;
import java.util.List;

public interface OrderItemCustomRepository {
	List<OrderItem> findOrderList(OrderSearchFilter filter);
}
