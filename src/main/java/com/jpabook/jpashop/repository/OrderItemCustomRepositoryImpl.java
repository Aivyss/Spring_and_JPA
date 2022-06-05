package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.delivery.QDelivery;
import com.jpabook.jpashop.domain.item.QItem;
import com.jpabook.jpashop.domain.member.QMember;
import com.jpabook.jpashop.domain.order.OrderItem;
import com.jpabook.jpashop.domain.order.OrderStatus;
import com.jpabook.jpashop.domain.order.QOrder;
import com.jpabook.jpashop.domain.order.QOrderItem;
import com.jpabook.jpashop.dto.OrderSearchFilter;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemCustomRepositoryImpl implements OrderItemCustomRepository{
	private final JPAQueryFactory queryFactory;
	
	@Override
	public List<OrderItem> findOrderList(OrderSearchFilter filter) {
		QOrderItem orderItem = QOrderItem.orderItem;
		QItem item = QItem.item;
		QOrder order = QOrder.order;
		QDelivery delivery = QDelivery.delivery;
		QMember member = QMember.member;
		
		final String memberName = filter.getMemberName();
		final OrderStatus orderStatus = filter.getOrderStatus();
		
		return queryFactory.selectFrom(orderItem)
			.innerJoin(orderItem.item, item).fetchJoin()
			.innerJoin(orderItem.order, order).fetchJoin()
			.innerJoin(order.member, member).fetchJoin()
			.innerJoin(order.delivery, delivery).fetchJoin()
			.where(
				member.name.like(
						"%" + Optional.ofNullable(memberName).orElse("") + "%")
					.and(orderStatus == null ? orderItem.eq(orderItem) : order.status.eq(orderStatus))
			)
			.fetch();
	}
}
