package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.item.Item;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * <ul>
 *     <li> ITEM Table Access Class </li>
 * </ul>
 */
@Component
@RequiredArgsConstructor
public class ItemCustomRepositoryImpl implements ItemCustomRepository {
	private final EntityManager em;
	private final JPAQueryFactory queryFactory;
	
	public void persistOrMerge(Item item) {
		if (item.getId() == null) {
			em.persist(item);
		} else {
			em.merge(item); // 실무에서 merge를 쓰는 것은 굉장히 위험하다. dirty checking을 이용할 것. 연습상 이렇게 한 것임
		}
	}
}
