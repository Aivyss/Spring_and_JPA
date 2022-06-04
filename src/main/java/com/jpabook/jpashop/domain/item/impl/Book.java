package com.jpabook.jpashop.domain.item.impl;

import com.jpabook.jpashop.domain.common.Edits;
import com.jpabook.jpashop.domain.item.Item;
import java.util.Arrays;
import java.util.List;
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
	
	public Book(Long id, String name, int stockQuantity, int price, String author, String isbn,
		Edits edit) {
		super(id, name, stockQuantity, price, edit);
		this.author = author;
		this.isbn = isbn;
	}
	
	// * default constructor for JPA
	protected Book() {
	}
	
	public static Book create(String name, int stockQuantity, int price, String author,
		String isbn) {
		return new Book(null, name, stockQuantity, price, author, isbn, Edits.newEdits(null));
	}
	
	public static Book newEntityAlreadyExist(Long id, String name, int stockQuantity, int price, String author,
		String isbn) {
		return new Book(id, name, stockQuantity, price, author, isbn, Edits.newEdits(null));
	}
	
	public void updateBookInfo(String name, String author, String isbn, int price, int stockQuantity) {
		final List<Boolean> changedList = Arrays.asList(!this.author.equals(author),
			!this.name.equals(name), !this.isbn.equals(isbn), !(this.price == price),
			!(this.stockQuantity == stockQuantity));
		this.name = name;
		this.author = author;
		this.isbn = isbn;
		this.price = price;
		this.stockQuantity = stockQuantity;
		final Boolean isChanged = changedList.stream().reduce((prev, curr) -> prev || curr).get();
		
		if (isChanged) {
			this.edits = Edits.newEdits(null);
		}
	}
}
