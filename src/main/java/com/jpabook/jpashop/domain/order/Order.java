package com.jpabook.jpashop.domain.order;

import static com.jpabook.jpashop.domain.sequence.SequenceGenerators.ORDER_SEQ_GEN;
import static com.jpabook.jpashop.domain.sequence.Sequences.ORDER_SEQ;

import com.jpabook.jpashop.domain.delivery.Delivery;
import com.jpabook.jpashop.domain.member.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@SequenceGenerator(name = ORDER_SEQ_GEN, sequenceName = ORDER_SEQ, initialValue = 1, allocationSize = 50)
@Table(name = "ORDERS")
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
	
	@JoinColumn(name = "MEMBER_ID")
	@ManyToOne
	private Member member;
	
	@OneToOne(mappedBy = "order")
	private Delivery delivery;
	
	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems = new ArrayList<>();
}
