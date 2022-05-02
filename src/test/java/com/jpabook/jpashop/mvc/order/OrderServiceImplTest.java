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
import com.jpabook.jpashop.exception.NotEnoughStockException;
import com.jpabook.jpashop.mvc.item.ItemRepository;
import com.jpabook.jpashop.mvc.member.MemberRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

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
	@Rollback(value = false)
	public void testSave() {
	    // * given
		String memberName = "memberA";
		String memberNickname = "nicknameA";
		Member member = new Member(
			null,
			memberName,
			memberNickname,
			new Address("city", "street", "10-1010"),
			new Edits(LocalDateTime.now(), DeletedFlag.N, null)
		);
		Edits edit = new Edits(LocalDateTime.now(), DeletedFlag.N, member);
		Book book = new Book(
			null, "book_name", 100, 5000,
			"author_name", "isbn", edit
		);
		int count = 5;
		
		memberRepository.save(member);
		itemRepository.save(book);
		
	    // * when
		final Long id = orderService.createOrder(member.getId(), book.getId(), count);
		
		// * then
		final Order actualOrder = orderRepository.findOne(id);
		assertThat(id).isGreaterThan(0L);
		assertThat(actualOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
		assertThat(actualOrder.getDelivery().getStatus()).isEqualTo(DeliveryStatus.READY);
		actualOrder.getOrderItems().forEach(orderItem -> {
			assertThat(orderItem.getCount()).isEqualTo(count);
			assertThat(orderItem.getTotalPrice()).isEqualTo(count * orderItem.getItem().getPrice());
		});
	}
	
	@Test
	@DisplayName("상품 주문 취소")
	public void testCancelOrder() {
		// * given
		String memberName = "memberA";
		String memberNickname = "nicknameA";
		Member member = new Member(
			null,
			memberName,
			memberNickname,
			new Address("city", "street", "10-1010"),
			new Edits(LocalDateTime.now(), DeletedFlag.N, null)
		);
		Edits edit = new Edits(LocalDateTime.now(), DeletedFlag.N, member);
		Book book = new Book(
			null, "book_name", 100, 5000,
			"author_name", "isbn", edit
		);
		int count = 5;
		
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
	public void testExceedStock() {
		// * given
		int totalCount = 1;
		String memberName = "memberA";
		String memberNickname = "nicknameA";
		Member member = new Member(
			null,
			memberName,
			memberNickname,
			new Address("city", "street", "10-1010"),
			new Edits(LocalDateTime.now(), DeletedFlag.N, null)
		);
		Edits edit = new Edits(LocalDateTime.now(), DeletedFlag.N, member);
		Book book = new Book(
			null, "book_name", totalCount, 5000,
			"author_name", "isbn", edit
		);
		
		memberRepository.save(member);
		itemRepository.save(book);
		assertThatThrownBy(
			() -> orderService.createOrder(member.getId(), book.getId(), 10000)
		).isInstanceOf(NotEnoughStockException.class).hasMessage("need more stock");
		
		
	}
}
