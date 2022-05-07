package com.jpabook.jpashop.mvc.order;

import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.domain.item.impl.Book;
import com.jpabook.jpashop.domain.member.Member;
import com.jpabook.jpashop.dto.BookForm;
import com.jpabook.jpashop.dto.MemberForm;
import com.jpabook.jpashop.dto.OrderForm;
import com.jpabook.jpashop.i18n.MessageService;
import com.jpabook.jpashop.mvc.item.ItemService;
import com.jpabook.jpashop.mvc.member.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.lang.reflect.Field;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Api(value = "Order API V1")
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class OrderController {
	
	private final ItemService itemService;
	private final MemberService memberService;
	private final OrderService orderService;
	private final MessageService messageService;
	
	@ApiOperation(
		httpMethod = "GET",
		value = "오더 생성 폼 렌더링 메소드",
		notes = "렌더링 할 뷰 템플릿 매핑",
		tags = {"render"}
	)
	@GetMapping("/new")
	public String viewOrderForm(Model model) {
		final OrderForm orderForm = new OrderForm();
		
		final List<Item> items = itemService.findItems();
		final List<Member> allMembers = memberService.findAllMembers();
		final List<BookForm> bookForms = items.stream()
			.map(item -> BookForm.entityToForm((Book) item)).toList();
		final List<MemberForm> memberForms = allMembers.stream()
			.map(member -> MemberForm.memberToForm(member)).toList();
		
		orderForm.setItems(bookForms);
		orderForm.setMembers(memberForms);
		orderForm.setMemberIdError((String) model.getAttribute("memberIdError"));
		orderForm.setItemIdError((String) model.getAttribute("itemIdError"));
		orderForm.setCountError((String) model.getAttribute("countError"));
		model.addAttribute("form", orderForm);
		
		
		return "order/order-form";
	}
	
	@PostMapping("/new")
	public String saveOrder(@Valid OrderForm form, BindingResult result,
		RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			final List<FieldError> fieldErrors = result.getFieldErrors();
			fieldErrors.stream().forEach(error -> {
				final String fieldName = error.getField();
				final Class<OrderForm> orderFormClass = OrderForm.class;
				final Field field;
				try {
					field = orderFormClass.getDeclaredField(fieldName);
					final Min minAnnotation = field.getAnnotation(Min.class);
					String errorMessageId = minAnnotation.message();
					errorMessageId = errorMessageId.substring(1, errorMessageId.length() - 1);
					final String errorMessage = messageService.convertMessage(errorMessageId, null);
					redirectAttributes.addFlashAttribute(fieldName + "Error", errorMessage);
				} catch (NoSuchFieldException e) {
					throw new RuntimeException(e);
				}
			});
			
			return "redirect:/order/new";
		}
		
		orderService.createOrder(form.getMemberId(), form.getItemId(), form.getCount());
		
		return "redirect:/";
	}
	
	@GetMapping("/list")
	public String viewOrderList(Model model) {
		orderService.searchOrders(null);
		return "order/order-list";
	}
}