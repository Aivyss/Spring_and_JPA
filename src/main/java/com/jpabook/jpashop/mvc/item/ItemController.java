package com.jpabook.jpashop.mvc.item;

import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.dto.BookForm;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;

	@GetMapping("/new")
	public String viewItemForm(Model model) {
		final BookForm bookForm = new BookForm();
		model.addAttribute("bookForm", bookForm);
		
		return "items/item-form";
	}
	
	@PostMapping("/new")
	public String addItem(@Valid BookForm bookForm, BindingResult result) {
		if (result.hasErrors()) return "items/item-form";
		
		final Item item = BookForm.formToEntity(bookForm);
		itemService.save(item);
		
		return "redirect:/";
	}
}
