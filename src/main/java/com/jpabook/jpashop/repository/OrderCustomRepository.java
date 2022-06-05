package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.order.Order;
import com.jpabook.jpashop.dto.OrderSearchFilter;
import java.util.List;

public interface OrderCustomRepository {
	
	/**
	 * <h1> V1 쿼리의 문제 </h1>
	 * <ul>
	 *     <li> fetch join이 없어서 N+1 문제가 발생 </li>
	 *     <li> Delivery가 OneToOne 양방향이라 LAZY로 설정해도 EAGER로 강제됨 </li>
	 * </ul>
	 * @param filter 검색필터
	 * @return order list
	 */
	List<Order> searchOrdersV1(OrderSearchFilter filter);
}
