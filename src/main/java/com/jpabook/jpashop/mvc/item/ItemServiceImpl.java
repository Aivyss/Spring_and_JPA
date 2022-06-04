package com.jpabook.jpashop.mvc.item;

import static com.jpabook.jpashop.exception.ExceptionCase.NOT_FOUND_DATA;

import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.exception.ExceptionSupplierUtils;
import com.jpabook.jpashop.repository.ItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
	private final ItemRepository itemRepository;
	private final ExceptionSupplierUtils exceptions;
	
	@Override
	@Transactional
	public void save(Item item) {
		itemRepository.persistOrMerge(item);
	}
	
	@Override
	public List<Item> findItems() {
		return itemRepository.findAll();
	}
	
	@Override
	public Item findOne(Long id) {
		return itemRepository.findById(id).orElseThrow(exceptions.getExceptionSupplier(NOT_FOUND_DATA));
	}
}
