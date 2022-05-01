package com.jpabook.jpashop.domain.item.impl;

import com.jpabook.jpashop.domain.common.Edits;
import com.jpabook.jpashop.domain.item.Item;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Setter;

@Entity
@Setter
@DiscriminatorValue("MOVIE")
@SuppressWarnings("JpaDataSourceORMInspection")
public class Movie extends Item {
	
	@Column(name = "DIRECTOR")
	private String director;
	@Column(name = "ACTOR")
	private String actor;
	
	public Movie(Long id, String name, int stockQuantity, int price, String director, String actor, Edits edit) {
		super(id, name, stockQuantity, price, edit);
		this.director = director;
		this.actor = actor;
	}
	
	// * default constructor for JPA
	public Movie() {
	
	}
}
