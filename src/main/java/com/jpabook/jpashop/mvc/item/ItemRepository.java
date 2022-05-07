package com.jpabook.jpashop.mvc.item;

import com.jpabook.jpashop.domain.item.Item;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * <ul>
 *     <li> ITEM Table Access Class </li>
 * </ul>
 */
@Repository
@RequiredArgsConstructor
public class ItemRepository {
	private final EntityManager em;
	
	public void save(Item item) {
		if (item.getId() == null) {
			em.persist(item);
		} else {
			em.merge(item); // 실무에서 merge를 쓰는 것은 굉장히 위험하다. dirty checking을 이용할 것. 연습상 이렇게 한 것임
		}
	}
	
	public Item findOne(Long id) {
		return em.find(Item.class, id);
	}
	
	public List<Item> findAll() {
		return em.createQuery("select i from Item i", Item.class).getResultList();
	}
}
