package com.jpabook.jpashop.domain.member;

import static com.jpabook.jpashop.domain.sequence.SequenceGenerators.MEM_SEQ_GEN;
import static com.jpabook.jpashop.domain.sequence.Sequences.MEM_SEQ;

import com.jpabook.jpashop.domain.common.Address;
import com.jpabook.jpashop.domain.common.Edits;
import com.jpabook.jpashop.domain.order.Order;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@SequenceGenerator(name = MEM_SEQ_GEN, sequenceName = MEM_SEQ, initialValue = 1, allocationSize = 100)
public class Member {
	
	@Id
	@Column(name = "MEMBER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = MEM_SEQ_GEN)
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Embedded
	private Address address;
	
	@Embedded
	private Edits edits;
	
	@OneToMany(mappedBy = "member") // 호스트 클래스의 필드변수명
	private List<Order> orders = new ArrayList<>();
}
