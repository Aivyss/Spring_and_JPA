package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.item.Item;

public interface ItemCustomRepository {
	void persistOrMerge(Item item);
}
