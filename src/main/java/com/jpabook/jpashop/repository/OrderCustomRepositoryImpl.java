package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.member.QMember;
import com.jpabook.jpashop.domain.order.Order;
import com.jpabook.jpashop.domain.order.OrderStatus;
import com.jpabook.jpashop.domain.order.QOrder;
import com.jpabook.jpashop.dto.OrderSearchFilter;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("ToArrayCallWithZeroLengthArrayArgument")
@Repository
@RequiredArgsConstructor
public class OrderCustomRepositoryImpl implements OrderCustomRepository {
	
	private final JPAQueryFactory queryFactory;
	
	@Override
	public List<Order> searchOrders(OrderSearchFilter filter) {
		QOrder order = QOrder.order;
		QMember member = QMember.member;
		final String memberName = filter.getMemberName();
		final OrderStatus orderStatus = filter.getOrderStatus();
		BooleanExpression whereCondition = member.name.like(
			"%" + Optional.ofNullable(memberName).orElse("") + "%");
		
		if (orderStatus != null) {
			whereCondition = whereCondition.and(order.status.eq(orderStatus));
		}
		
		
		return queryFactory.selectFrom(order)
			.innerJoin(order.member, member).fetchJoin()
			.where(whereCondition).fetch();
	}
}
