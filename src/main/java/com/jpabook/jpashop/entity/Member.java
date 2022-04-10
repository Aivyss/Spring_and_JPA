package com.jpabook.jpashop.entity;

import static com.jpabook.jpashop.entity.sequence.SequenceGenerators.MEM_SEQ_GEN;
import static com.jpabook.jpashop.entity.sequence.Sequences.MEM_SEQ;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
}
