package com.jpabook.jpashop.domain.delivery;

import static com.jpabook.jpashop.domain.sequence.SequenceGenerators.DELIVERY_SEQ_GEN;
import static com.jpabook.jpashop.domain.sequence.Sequences.DELIVERY_SEQ;

import com.jpabook.jpashop.domain.common.Address;
import com.jpabook.jpashop.domain.common.DeletedFlag;
import com.jpabook.jpashop.domain.common.Edits;
import com.jpabook.jpashop.domain.member.Member;
import com.jpabook.jpashop.domain.order.Order;
import java.time.LocalDateTime;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@SequenceGenerator(name = DELIVERY_SEQ_GEN, sequenceName = DELIVERY_SEQ, initialValue = 1, allocationSize = 50)
@SuppressWarnings({"JpaDataSourceORMInspection", "DefaultAnnotationParam", "unused"})
public class Delivery {
	
	@Id
	@GeneratedValue(generator = DELIVERY_SEQ_GEN, strategy = GenerationType.SEQUENCE)
	@Column(name = "DELIVERY_ID")
	private Long id;
	
	@Embedded
	private Address address;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "DELIVERY_STATUS")
	private DeliveryStatus status;
	
	@Embedded
	private Edits edits;
	
	@JoinColumn(name = "ORDER_ID")
	@OneToOne(fetch = FetchType.LAZY)
	private Order order;
	
	// * default constructor for JPA
	protected Delivery () {}
	
	public Delivery(Long id, Address address, DeliveryStatus status, Edits edits, Order order) {
		this.id = id;
		this.address = address;
		this.status = status;
		this.edits = edits;
		this.order = order;
	}
	
	public static Delivery newDelivery(Member member, Order order) {
		return new Delivery(
			null,
			member.getAddress(),
			DeliveryStatus.READY,
			new Edits(LocalDateTime.now(), DeletedFlag.N, member),
			order
		);
	}
	
	// * setters
	public void setStatus(DeliveryStatus status) {
		this.status = status;
	}
}
