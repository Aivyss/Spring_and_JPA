package com.jpabook.jpashop.domain.item.impl;

import com.jpabook.jpashop.domain.item.Item;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@DiscriminatorValue("BOOK")
@SuppressWarnings("JpaDataSourceORMInspection")

public class Book extends Item {
	
	@Column(name = "AUTHOR")
	private String author;
	@Column(name = "ISBN")
	private String isbn;
}
