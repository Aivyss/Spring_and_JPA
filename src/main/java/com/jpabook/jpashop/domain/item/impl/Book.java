package com.jpabook.jpashop.domain.item.impl;

import com.jpabook.jpashop.domain.common.Edits;
import com.jpabook.jpashop.domain.item.Item;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("BOOK")
@SuppressWarnings("JpaDataSourceORMInspection")

public class Book extends Item {
	
	@Column(name = "AUTHOR")
	private String author;
	@Column(name = "ISBN")
	private String isbn;
	
	public Book(Long id, String name, int stockQuantity, int price, String author, String isbn, Edits edit) {
		super(id, name, stockQuantity, price, edit);
		this.author = author;
		this.isbn = isbn;
	}
	
	// * default constructor for JPA
	public Book() {
	
	}
}
