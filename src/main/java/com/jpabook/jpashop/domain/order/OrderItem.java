package com.jpabook.jpashop.domain.order;

import static com.jpabook.jpashop.domain.sequence.SequenceGenerators.ORDER_ITEM_SEQ_GEN;
import static com.jpabook.jpashop.domain.sequence.Sequences.ORDER_ITEM_SEQ;

import com.jpabook.jpashop.domain.common.Edits;
import com.jpabook.jpashop.domain.item.Item;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@SequenceGenerator(name = ORDER_ITEM_SEQ_GEN, sequenceName = ORDER_ITEM_SEQ, initialValue = 1, allocationSize = 50)
@SuppressWarnings({"JpaDataSourceORMInspection", "DefaultAnnotationParam", "unused", "FieldMayBeFinal"})
public class OrderItem {
	@Id
	@GeneratedValue(generator = ORDER_ITEM_SEQ_GEN, strategy = GenerationType.SEQUENCE)
	@Column(name = "ORDER_ITEM_ID")
	private Long id;
	
	@JoinColumn(name = "ITEM_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Item item;
	
	@JoinColumn(name = "ORDER_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Order order;
	
	@Embedded
	private Edits edits;
	
	@Column(name = "ORDER_PRICE")
	private int orderPrice;
	
	@Column(name="ITEM_COUNT")
	private int count;
}
