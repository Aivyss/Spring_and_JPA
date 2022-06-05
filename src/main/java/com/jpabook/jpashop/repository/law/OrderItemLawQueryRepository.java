package com.jpabook.jpashop.repository.law;

import com.jpabook.jpashop.dto.OrderListForm;
import com.jpabook.jpashop.dto.OrderSearchFilter;
import java.util.List;

public interface OrderItemLawQueryRepository {
	/**
	 * 곧바로 dto로 조회하는 방법. queryDSL Projection은 알고 있으므로 순수 JPQL을 이용
	 * @param filter 조회필터
	 * @return dtos
	 */
	List<OrderListForm> findOrderListV3(OrderSearchFilter filter);
}
