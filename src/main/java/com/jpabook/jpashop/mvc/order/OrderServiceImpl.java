package com.jpabook.jpashop.mvc.order;

import com.jpabook.jpashop.domain.delivery.Delivery;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.domain.member.Member;
import com.jpabook.jpashop.domain.order.Order;
import com.jpabook.jpashop.domain.order.OrderItem;
import com.jpabook.jpashop.mvc.item.ItemRepository;
import com.jpabook.jpashop.mvc.member.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;
	
	@Override
	@Transactional
	public Long createOrder(Long memberId, Long itemId, int count) {
		final Member member = memberRepository.findOne(memberId);
		final Item item = itemRepository.findOne(itemId);
		final Delivery delivery = Delivery.newDelivery(member, null);
		final OrderItem orderItem = OrderItem.newOrderItem(item, null, member, count);
		final Order order = Order.newOrder(member, delivery, orderItem);
		
		orderRepository.save(order);
		
		return order.getId();
	}
	
	@Override
	@Transactional
	public void cancelOrder(Long orderId) {
		final Order order = orderRepository.findOne(orderId);
		order.cancel(); // due to dirty check
	}
	
	@Override
	public List<Order> findOrders() {
		return null;
	}
}
