package com.jpabook.jpashop.mvc.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jpabook.jpashop.domain.common.Address;
import com.jpabook.jpashop.domain.common.DeletedFlag;
import com.jpabook.jpashop.domain.common.Edits;
import com.jpabook.jpashop.domain.delivery.DeliveryStatus;
import com.jpabook.jpashop.domain.item.impl.Book;
import com.jpabook.jpashop.domain.member.Member;
import com.jpabook.jpashop.domain.order.Order;
import com.jpabook.jpashop.domain.order.OrderStatus;
import com.jpabook.jpashop.dto.OrderSearchFilter;
import com.jpabook.jpashop.exception.NotEnoughStockException;
import com.jpabook.jpashop.interfaces.exceptions.JPAShopError;
import com.jpabook.jpashop.mvc.item.ItemRepository;
import com.jpabook.jpashop.mvc.member.MemberRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * OrderService는 단위별로 쪼개지 않았다. DB에 대해서 직접 로직에 의해서 삽입되는지 테스트하기 위해 통합테스트로 진행함.
 */
@SpringBootTest
@Transactional(readOnly = true)
public class OrderServiceImplTest {
	
	@Autowired
	private OrderServiceImpl orderService;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ItemRepository itemRepository;
	
	@Test
	@DisplayName("상품 주문")
	@Transactional
	public void testSave() {
		// * given
		String memberName = "memberA";
		String memberNickname = "nicknameA";
		int stockQuantity = 100;
		int count = 5;
		Member member = getMember(memberName, memberNickname);
		Book book = getBook(member, stockQuantity);
		
		memberRepository.save(member);
		itemRepository.save(book);
		
		// * when
		final Long id = orderService.createOrder(member.getId(), book.getId(), count);
		
		// * then
		final Order actualOrder = orderRepository.findOne(id);
		assertThat(id).isGreaterThan(0L);
		assertThat(actualOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
		assertThat(actualOrder.getDelivery().getStatus()).isEqualTo(DeliveryStatus.READY);
		assertThat(actualOrder.getOrderItems().get(0).getItem().getStockQuantity()).isSameAs(
			stockQuantity - count);
		actualOrder.getOrderItems().forEach(orderItem -> {
			assertThat(orderItem.getCount()).isEqualTo(count);
			assertThat(orderItem.getTotalPrice()).isEqualTo(count * orderItem.getItem().getPrice());
		});
	}
	
	@Test
	@DisplayName("상품 주문 취소")
	@Transactional
	public void testCancelOrder() {
		// * given
		String memberName = "memberA";
		String memberNickname = "nicknameA";
		int stockQuantity = 100;
		int count = 5;
		Member member = getMember(memberName, memberNickname);
		Book book = getBook(member, stockQuantity);
		
		memberRepository.save(member);
		itemRepository.save(book);
		final Long id = orderService.createOrder(member.getId(), book.getId(), count);
		
		// * when
		final Order order = orderRepository.findOne(id);
		order.cancel();
		
		// * then
		assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);
		assertThat(order.getDelivery().getStatus()).isEqualTo(DeliveryStatus.CANCEL);
	}
	
	@Test
	@DisplayName("상품 주문 재고 수량 초과")
	@Transactional
	public void testExceedStock() {
		// * given
		String memberName = "memberA";
		String memberNickname = "nicknameA";
		int totalCount = 1;
		
		Member member = getMember(memberName, memberNickname);
		Book book = getBook(member, totalCount);
		
		memberRepository.save(member);
		itemRepository.save(book);
		assertThatThrownBy(
			() -> orderService.createOrder(member.getId(), book.getId(), 10000)
		).isInstanceOf(JPAShopError.class).isInstanceOf(NotEnoughStockException.class).hasMessage("need more stock");
	}
	
	@Test
	@DisplayName("검색 필터 테스트")
	@Transactional
	public void testSearchOrder() {
		// * given (pre setups)
		final Member admin = getMember("admin", "admin");
		memberRepository.save(admin);
		final Book book = getBook(admin, 10000);
		itemRepository.save(book);
		
		// * given
		final List<String> memberNames = Arrays.asList("ab2", "", null, "tst", "hbeia");
		final List<Member> members = new ArrayList<>();
		final List<Order> orders = new ArrayList<>();
		int i = 0;
		
		for (String name : memberNames) {
			final Member member = getMember(name,
				name == null || name.length() == 0 ? String.valueOf(i) : name);
			members.add(member);
			memberRepository.save(member);
			i += 1;
		}
		members.forEach(member -> orders.add(Order.newOrder(member)));
		orders.forEach(order -> orderRepository.save(order));
		
		final List<OrderSearchFilter> filters = new ArrayList<>();
		memberNames.forEach(name -> {
			final OrderSearchFilter filter1 = new OrderSearchFilter();
			final OrderSearchFilter filter2 = new OrderSearchFilter();
			final OrderSearchFilter filter3 = new OrderSearchFilter();
			filter1.setOrderStatus(OrderStatus.ORDER);
			filter1.setMemberName(name);
			filter2.setOrderStatus(OrderStatus.CANCEL);
			filter2.setMemberName(name);
			filter3.setOrderStatus(null);
			filter3.setMemberName(name);
			filters.add(filter1);
			filters.add(filter2);
			filters.add(filter3);
		});
		
		// * when, then
		filters.forEach(filter -> {
			final List<Order> actualOrders = orderService.searchOrders(filter);
			actualOrders.forEach(actualOrder -> assertThat(orders.contains(actualOrder)).isTrue());
		});
	}
	
	// * test util methods
	private Member getMember(String memberName, String memberNickname) {
		return new Member(
			null,
			memberName,
			memberNickname,
			new Address("city", "street", "10-1010"),
			new Edits(LocalDateTime.now(), DeletedFlag.N, null)
		);
	}
	
	private Book getBook(Member member, int stockQuantity) {
		Edits edit = new Edits(LocalDateTime.now(), DeletedFlag.N, member);
		return new Book(
			null, "book_name", stockQuantity, 5000,
			"author_name", "isbn", edit
		);
	}
}
