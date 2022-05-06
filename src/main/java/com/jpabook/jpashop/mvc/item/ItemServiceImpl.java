package com.jpabook.jpashop.mvc.item;

import com.jpabook.jpashop.domain.item.Item;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
	private final ItemRepository itemRepository;
	
	@Override
	@Transactional
	public void save(Item item) {
		itemRepository.save(item);
	}
	
	@Override
	public List<Item> findItems() {
		return itemRepository.findAll();
	}
	
	@Override
	public Item findOne(Long id) {
		return itemRepository.findOne(id);
	}
}
