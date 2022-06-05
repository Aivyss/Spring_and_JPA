package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.delivery.QDelivery;
import com.jpabook.jpashop.domain.item.QItem;
import com.jpabook.jpashop.domain.member.QMember;
import com.jpabook.jpashop.domain.order.OrderItem;
import com.jpabook.jpashop.domain.order.OrderStatus;
import com.jpabook.jpashop.domain.order.QOrder;
import com.jpabook.jpashop.domain.order.QOrderItem;
import com.jpabook.jpashop.dto.OrderListForm;
import com.jpabook.jpashop.dto.OrderSearchFilter;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemCustomRepositoryImpl implements OrderItemCustomRepository{
	private final JPAQueryFactory queryFactory;
	private final EntityManager em;
	
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
	
	@Override
	public List<OrderListForm> findOrderListV3(OrderSearchFilter filter) { // dto 반환도 그냥 QueryDSL을 쓰는게 낫다.
		String orderStatusCondition = "";
		final String name = filter.getMemberName();
		final OrderStatus orderStatus = filter.getOrderStatus();
		if (orderStatus != null) orderStatusCondition = "and o.status = :orderStatus";
		TypedQuery<OrderListForm> partialQuery = em.createQuery("select "
				+ "new com.jpabook.jpashop.dto.OrderListForm(oi.id, m.name, i.name, oi.orderPrice, oi.count, o.status, o.orderDate)"
				+ "from OrderItem oi "
				+ "join oi.order o "
				+ "join o.member m "
				+ "join o.delivery d "
				+ "join oi.item i "
				+ "where m.name like concat('%', :memberName, '%') "
				+ orderStatusCondition, OrderListForm.class)
			.setParameter("memberName", Optional.ofNullable(name).orElse(""));
		
		if (orderStatus != null) {
			partialQuery = partialQuery.setParameter("orderStatus", orderStatus);
		}
		
		return partialQuery.getResultList();
	}
}
