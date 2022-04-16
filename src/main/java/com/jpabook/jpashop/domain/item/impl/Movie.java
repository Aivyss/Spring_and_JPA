package com.jpabook.jpashop.domain.item.impl;

import com.jpabook.jpashop.domain.item.Item;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@DiscriminatorValue("MOVIE")
public class Movie extends Item {
	
	@Column(name = "DIRECTOR")
	private String director;
	@Column(name = "ACTOR")
	private String actor;
}
