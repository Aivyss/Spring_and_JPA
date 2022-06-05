package com.jpabook.jpashop.api;

import com.jpabook.jpashop.domain.order.Order;
import com.jpabook.jpashop.domain.order.OrderItem;
import com.jpabook.jpashop.dto.OrderListForm;
import com.jpabook.jpashop.dto.OrderSearchFilter;
import com.jpabook.jpashop.dto.api.common.DtoWrapper;
import com.jpabook.jpashop.repository.OrderItemRepository;
import com.jpabook.jpashop.repository.OrderRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * xToOne
 * Order -> Member (ManyToOne)
 * Order -> Delivery (OneToOne)
 *
 * xToMany
 * Order -> OrderItem
 */
@RestController
@RequiredArgsConstructor
public class OrderApiController {
	private final OrderRepository orderRepository; // JPA 성능최적화 연습을 위해 직접 임포트
	private final OrderItemRepository orderItemRepository;
	
	/**
	 * delivery는 OneToOne이므로 LAZY가 동작하지 않고 강제로 EAGER로 작동함
	 * 나머지는 Lazy loading 전략으로 프록시객체로 대체되어 있지만, DTO를 만드는 과정에서 호출하기 때문에 N+1문제를 일으킴
	 *
	 * @return dto
	 */
	@GetMapping("/api/v1/orders")
	public DtoWrapper<List<OrderListForm>> orderV1() {
		final List<Order> orders = orderRepository.searchOrdersV1(new OrderSearchFilter());
		final List<OrderListForm> dtos = orders.stream().map(OrderListForm::from)
			.flatMap(Collection::stream).collect(Collectors.toList());
		
		return new DtoWrapper<>(dtos);
	}
	
	/**
	 * fetch join을 이용해 최적화를 진행. N + 1 문제의 해결
	 *
	 * @return dtos
	 */
	@GetMapping("/api/v2/orders")
	public DtoWrapper<List<OrderListForm>> orderV2() {
		final List<OrderItem> orderItems = orderItemRepository.findOrderList(new OrderSearchFilter());
		final List<OrderListForm> dtos = orderItems.stream().map(OrderListForm::from).collect(Collectors.toList());
		
		return new DtoWrapper<>(dtos);
	}
	
	/**
	 * fetch join을 이용해 최적화를 진행. N + 1 문제의 해결
	 * dto로 곧바로 조회결과를 만들어 성능 최적화
	 *
	 * @return dtos
	 */
	@GetMapping("/api/v3/orders")
	public DtoWrapper<List<OrderListForm>> orderV3() {
		final List<OrderListForm> dtos  = orderItemRepository.findOrderListV3(new OrderSearchFilter());
		
		return new DtoWrapper<>(dtos);
	}
}
