package com.jpabook.jpashop.repository.law;

import com.jpabook.jpashop.domain.order.OrderStatus;
import com.jpabook.jpashop.dto.OrderListForm;
import com.jpabook.jpashop.dto.OrderSearchFilter;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemLawQueryRepositoryImpl implements OrderItemLawQueryRepository{
	private final EntityManager em;
	
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
