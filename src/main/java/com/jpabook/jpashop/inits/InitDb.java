package com.jpabook.jpashop.inits;

import com.jpabook.jpashop.domain.common.Address;
import com.jpabook.jpashop.domain.delivery.Delivery;
import com.jpabook.jpashop.domain.item.impl.Book;
import com.jpabook.jpashop.domain.member.Member;
import com.jpabook.jpashop.domain.order.Order;
import com.jpabook.jpashop.domain.order.OrderItem;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {
	
	private final InitService initService;
	
	@PostConstruct
	public void init() {
		initService.dtInit1();
		initService.dtInit2();
	}
	
	@Component
	@Transactional
	@RequiredArgsConstructor
	static class InitService {
		private final EntityManager em;
		
		/**
		 * userA
		 */
		public void dtInit1() {
			// * create users and persist
			final Address addressA = new Address("A", "A", "001");
			final Member userA = Member.create("userA", "userA", addressA);
			
			em.persist(userA);
			
			// * create book and persist
			final Book jpaBook1 = Book.create("JPA1 BOOK", 100, 10000, "author", "isbn");
			final Book jpaBook2 = Book.create("JPA2 BOOK", 100, 10000, "author", "isbn");
			
			em.persist(jpaBook1);
			em.persist(jpaBook2);
			
			// * create order & delivery
			final Order orderA = Order.create(userA);
			final OrderItem orderItemA_1 = OrderItem.create(jpaBook1, orderA, userA, 5);
			final OrderItem orderItemA_2 = OrderItem.create(jpaBook2, orderA, userA, 5);
			
			final Delivery deliveryA = Delivery.create(userA, orderA);
			
			orderA.addOrderItem(orderItemA_1);
			orderA.addOrderItem(orderItemA_2);
			orderA.setDelivery(deliveryA);
			
			em.persist(orderA);
			
			// * clear entity manager
			em.flush();
			em.clear();
		}
		
		/**
		 * userB
		 */
		public void dtInit2() {
			// * create users and persist
			final Address addressB = new Address("B", "B", "002");
			final Member userB = Member.create("userB", "userB", addressB);
			
			em.persist(userB);
			
			// * create book and persist
			final Book springBook1 = Book.create("Spring1 BOOK", 100, 10000, "author", "isbn");
			final Book springBook2 = Book.create("Spring2 BOOK", 100, 10000, "author", "isbn");
			
			em.persist(springBook1);
			em.persist(springBook2);
			
			// * create order & delivery
			final Order orderB = Order.create(userB);
			final OrderItem orderItemB_1 = OrderItem.create(springBook1, orderB, userB, 5);
			final OrderItem orderItemB_2 = OrderItem.create(springBook2, orderB, userB, 5);
			
			final Delivery deliveryB = Delivery.create(userB, orderB);
			
			orderB.addOrderItem(orderItemB_1);
			orderB.addOrderItem(orderItemB_2);
			orderB.setDelivery(deliveryB);
			orderB.setDelivery(deliveryB);
			
			em.persist(orderB);
			
			// * clear entity manager
			em.flush();
			em.clear();
		}
	}
}


