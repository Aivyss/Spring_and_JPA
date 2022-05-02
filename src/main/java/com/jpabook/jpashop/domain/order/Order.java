package com.jpabook.jpashop.domain.order;

import static com.jpabook.jpashop.domain.sequence.SequenceGenerators.ORDER_SEQ_GEN;
import static com.jpabook.jpashop.domain.sequence.Sequences.ORDER_SEQ;

import com.jpabook.jpashop.domain.common.DeletedFlag;
import com.jpabook.jpashop.domain.common.Edits;
import com.jpabook.jpashop.domain.delivery.Delivery;
import com.jpabook.jpashop.domain.delivery.DeliveryStatus;
import com.jpabook.jpashop.domain.member.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@SequenceGenerator(name = ORDER_SEQ_GEN, sequenceName = ORDER_SEQ, initialValue = 1, allocationSize = 50)
@Table(name = "ORDERS")
@SuppressWarnings({"JpaDataSourceORMInspection", "DefaultAnnotationParam", "unused",
	"FieldMayBeFinal"})
public class Order {
	
	@Id
	@GeneratedValue(generator = ORDER_SEQ_GEN, strategy = GenerationType.SEQUENCE)
	@Column(name = "ORDER_ID")
	private Long id;
	
	@Column(name = "ORDER_DATE")
	private LocalDateTime orderDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ORDER_STATUS")
	private OrderStatus status;
	
	@Embedded
	private Edits edits;
	
	@JoinColumn(name = "MEMBER_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;
	
	/**
	 * <ul>
	 *     <li> CascadeType.ALL </li>
	 *     <li> order를 persist할 때 이 옵션의 것까지 모두 persist </li>
	 * </ul>
	 */
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Delivery delivery;
	
	/**
	 * <ul>
	 *     <li> CascadeType.ALL </li>
	 *     <li> order를 persist할 때 이 옵션의 것까지 모두 persist </li>
	 * </ul>
	 */
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();
	
	// * default constructor for JPA
	protected Order() {
	}
	
	public Order(Long id, Member member, Delivery delivery, Edits edits, LocalDateTime orderDate,
		OrderStatus orderStatus, OrderItem... orderItems) {
		this.id = id;
		this.member = member;
		this.delivery = delivery;
		this.edits = edits;
		this.orderDate = orderDate;
		this.status = orderStatus;
		this.orderItems = Arrays.stream(orderItems).toList();
	}
	
	// * relation util methods
	public void setMember(Member member) {
		this.member = member;
		member.getOrders().add(this);
	}
	
	public void addOrderItem(OrderItem orderItem) {
		orderItem.setOrder(this);
		this.orderItems.add(orderItem);
	}
	
	// * simple business logics
	public static Order newOrder(Member member, Delivery delivery, OrderItem... orderItems) {
		final LocalDateTime currentTime = LocalDateTime.now();
		return new Order(
			null,
			member,
			delivery,
			new Edits(currentTime, DeletedFlag.N, member),
			currentTime,
			OrderStatus.ORDER,
			orderItems
		);
	}
	
	public void cancel() {
		if (delivery.getStatus() == DeliveryStatus.COMP) {
			throw new IllegalStateException("이미 배송 완료된 건으로 취소가 불가능합니다.");
		}
		
		status = OrderStatus.CANCEL;
		delivery.setStatus(DeliveryStatus.CANCEL);
		orderItems.forEach(OrderItem::cancel);
	}
	
	public int getTotalPrice() {
		return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
	}
}
