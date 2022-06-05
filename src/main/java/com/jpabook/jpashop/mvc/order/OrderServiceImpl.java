package com.jpabook.jpashop.mvc.order;

import static com.jpabook.jpashop.exception.ExceptionCase.NOT_FOUND_DATA;

import com.jpabook.jpashop.domain.delivery.Delivery;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.domain.member.Member;
import com.jpabook.jpashop.domain.order.Order;
import com.jpabook.jpashop.domain.order.OrderItem;
import com.jpabook.jpashop.dto.OrderSearchFilter;
import com.jpabook.jpashop.exception.ExceptionSupplierUtils;
import com.jpabook.jpashop.repository.ItemRepository;
import com.jpabook.jpashop.repository.MemberRepository;
import com.jpabook.jpashop.repository.OrderItemRepository;
import com.jpabook.jpashop.repository.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;
	private final ExceptionSupplierUtils exceptions;
	
	@Override
	@Transactional
	public Long createOrder(Long memberId, Long itemId, int count) {
		final Member member = memberRepository.findById(memberId).orElseThrow();
		final Item item = itemRepository.findById(itemId).orElseThrow();
		final Delivery delivery = Delivery.create(member, null);
		final OrderItem orderItem = OrderItem.create(item, null, member, count);
		final Order order = Order.create(member);
		order.addOrderItem(orderItem); // 양방향 연관관계 때문에 필요
		order.setDelivery(delivery); // 양방향 연관관계 때문에 필요
		
		/*
			양방향 연관관계를 되도록 설정 안하는게 가장 베스트이지만 만약에 필요하다면, 연관관계 편의 메소드를 만든다.
		    이 때 편의 메소드를 만드는 기준은 사람마다 다르지만 나의 경우 연관관계의 주인이 아닌 쪽을 선호한다.
		    여기서 Delivery, OrderItem은 연관관계의 주인이지만 Order는 연관관계의 주인이 아니다. 따라서 order에
		    편의 메소드를 몰아 넣었다. 위의 편의 메소드를 적용하지 않으면 연관관계 주인들에 해당하는 row의 foreign key가
		    모두 null로 들어간다.
		 */
		
		orderRepository.save(order);
		
		return order.getId();
	}
	
	@Override
	@Transactional
	public void cancelOrder(Long orderId) {
		final Order order = orderRepository.findById(orderId)
			.orElseThrow(exceptions.getExceptionSupplier(NOT_FOUND_DATA));
		order.cancel(); // due to dirty check
	}
	
	@Override
	public List<OrderItem> searchOrders(OrderSearchFilter filter) {
//		return orderRepository.searchOrders(filter);
		return orderItemRepository.findOrderList(filter);
	}
}
