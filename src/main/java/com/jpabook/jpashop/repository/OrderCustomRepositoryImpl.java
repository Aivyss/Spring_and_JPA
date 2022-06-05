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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("ToArrayCallWithZeroLengthArrayArgument")
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderCustomRepositoryImpl implements OrderCustomRepository {
	
	private final JPAQueryFactory queryFactory;
	
	@Override
	public List<Order> searchOrdersV1(OrderSearchFilter filter) {
		QOrder order = QOrder.order;
		QMember member = QMember.member;
		String memberName = Optional.ofNullable(filter.getMemberName()).orElse("");
		final OrderStatus orderStatus = filter.getOrderStatus();
		final BooleanExpression whereCondition = orderStatus == null ? order.eq(order) : order.status.eq(orderStatus);
		
		return queryFactory.selectFrom(order)
			.innerJoin(order.member, member).on(member.name.like("%" + memberName + "%"))
			.where(whereCondition)
			.fetch();
	}
}
