package com.jpabook.jpashop.mvc.order;

import com.jpabook.jpashop.domain.order.Order;
import com.jpabook.jpashop.dto.OrderSearchFilter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("ToArrayCallWithZeroLengthArrayArgument")
@Repository
@RequiredArgsConstructor
public class OrderRepository {
	
	private final EntityManager em;
	
	public void save(Order order) {
		em.persist(order);
	}
	
	public Order findOne(Long id) {
		return em.find(Order.class, id);
	}
	
	public List<Order> searchOrders(OrderSearchFilter filter) {
		/*
			유지보수가 거의 불가능에 가까우므로
			추후에 QueryDSL을 이용해 수정할 예정.
		 */
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<Order> cq = cb.createQuery(Order.class);
		final Root<Order> o = cq.from(Order.class);
		final Join<Object, Object> m = o.join("member", JoinType.INNER);
		List<Predicate> criteria = new ArrayList<>();
		
		// 주문 상태 검색
		if (filter.getOrderStatus() != null) {
			final Predicate status = cb.equal(o.get("status"), filter.getOrderStatus());
			criteria.add(status);
		}
		
		// 회원 이름 검색
		if (filter.getMemberName() != null && filter.getMemberName().length() > 0) {
			final Predicate name = cb.like(m.get("name"), "%" + filter.getMemberName() + "%");
			criteria.add(name);
		}
		
		cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
		final TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
		return query.getResultList();
	}
}
