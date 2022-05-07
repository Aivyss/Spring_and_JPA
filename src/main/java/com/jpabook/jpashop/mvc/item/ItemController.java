package com.jpabook.jpashop.mvc.item;

import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.domain.item.impl.Book;
import com.jpabook.jpashop.dto.BookForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
@Api(value = "Item Controller V1")
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;
	
	@ApiOperation(
		httpMethod = "GET",
		value = "아이템 폼 렌더링 메소드",
		notes = "렌더링 할 뷰 템플릿 매핑",
		tags=  {"render"}
	)
	@GetMapping("/new")
	public String viewItemForm(Model model) {
		final BookForm bookForm = new BookForm();
		model.addAttribute("bookForm", bookForm);
		
		return "items/item-form";
	}
	
	@PostMapping("/new")
	@ApiOperation(
		httpMethod = "POST",
		value = "북 폼 저장 비즈니스 로직",
		notes = "폼 저장 비즈니스 처리",
		tags=  {"business", "Item_entity", "Book_entity"}
	)
	public String addItem(@Valid BookForm bookForm, BindingResult result) {
		if (result.hasErrors()) return "items/item-form";
		
		final Item item = BookForm.formToEntity(bookForm);
		itemService.save(item);
		
		return "redirect:/";
	}
	
	@GetMapping("")
	@ApiOperation(
		httpMethod = "GET",
		value = "아이템 리스트 조회 뷰 렌더링",
		notes = "뷰 렌더링 메소드",
		tags = {"render"}
	)
	public String viewItemList(Model model) {
		final List<Item> items = itemService.findItems();
		model.addAttribute("items", items);
		
		return "items/item-list";
	}
	
	@GetMapping("/{itemId}/edit")
	@ApiOperation(
		httpMethod = "GET",
		value = "아이템 정보 수정 폼 렌더링",
		notes = "뷰 렌더링 메소드",
		tags = {"render"}
	)
	public String ViewEditItemForm(@PathVariable("itemId") long itemId, Model model) {
		final Item item = itemService.findOne(itemId);
		final BookForm bookForm = BookForm.entityToForm((Book) item);
		model.addAttribute("form", bookForm);
		
		return "items/edit-item";
	}
	
	@PostMapping("/{itemId}/edit")
	@ApiOperation(
		httpMethod = "POST",
		value = "아이템 정보 수정 비즈니스 로직",
		notes = "비즈니스 로직 메소드",
		tags = {"business", "Item_entity", "Book_entity"}
	)
	public String editItem(@ModelAttribute("form") BookForm form) {
		final Item item = BookForm.formToEntity(form);
		itemService.save(item);
		
		return "redirect:/items";
	}
}
