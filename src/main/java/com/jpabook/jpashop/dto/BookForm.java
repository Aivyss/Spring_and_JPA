package com.jpabook.jpashop.dto;

import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.domain.item.impl.Book;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {
	private Long id;
	@NotEmpty(message="{FORM_INSTANCE.BOOK_FORM.NOT_EMPTY.NAME}")
	private String name;
	@Min(value=1, message="{FORM_INSTANCE.BOOK_FORM.NOT_EMPTY.PRICE}")
	private int price;
	@Min(value=1, message="{FORM_INSTANCE.BOOK_FORM.NOT_EMPTY.STOCK_QUANTITY}")
	private int stockQuantity;
	@NotEmpty(message="{FORM_INSTANCE.BOOK_FORM.NOT_EMPTY.AUTHOR}")
	private String author;
	@NotEmpty(message="{FORM_INSTANCE.BOOK_FORM.NOT_EMPTY.ISBN}")
	private String isbn;
	
	public static Item formToEntity(BookForm form) {
		if (form.getId() == null || form.getId() < 1L) {
			return Book.newBook(form.getName(), form.stockQuantity, form.price, form.author, form.isbn);
		} else {
			return Book.newEntityAlreadyExist(form.id, form.name, form.stockQuantity, form.price, form.author, form.isbn);
		}
	}
	
	public static BookForm entityToForm(Book book) {
		final BookForm bookForm = new BookForm();
		bookForm.author = book.getAuthor();
		bookForm.name = book.getName();
		bookForm.price = book.getPrice();
		bookForm.stockQuantity = book.getStockQuantity();
		bookForm.isbn = book.getIsbn();
		bookForm.id = book.getId();
		
		return bookForm;
	}
}
