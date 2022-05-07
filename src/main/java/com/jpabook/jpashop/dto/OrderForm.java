package com.jpabook.jpashop.dto;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderForm {
	private Long orderId;
	@NotNull(message = "{FORM_INSTANCE.ORDER_FORM.VALIDATION.MEMBER}")
	@Min(message = "{FORM_INSTANCE.ORDER_FORM.VALIDATION.MEMBER}", value = 1)
	private Long memberId;
	@NotNull(message = "{FORM_INSTANCE.ORDER_FORM.VALIDATION.ITEM}")
	@Min(message = "{FORM_INSTANCE.ORDER_FORM.VALIDATION.MEMBER}", value = 1)
	private Long itemId;
	@Min(message = "{FORM_INSTANCE.ORDER_FORM.VALIDATION.COUNT}", value = 1)
	private int count;
	private String memberIdError;
	private String itemIdError;
	private String countError;
	
	private List<MemberForm> members;
	private List<BookForm> items;
}
