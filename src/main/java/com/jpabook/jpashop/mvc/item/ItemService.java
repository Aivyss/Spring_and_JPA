package com.jpabook.jpashop.mvc.item;

import com.jpabook.jpashop.domain.item.Item;
import java.util.List;

public interface ItemService {
	void save(Item item);
	
	List<Item> findItems();
	
	Item findOne(Long id);

}
