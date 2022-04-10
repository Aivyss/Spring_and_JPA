package com.jpabook.jpashop.persist;

import com.jpabook.jpashop.entity.Member;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MemberRepository {
	@PersistenceContext // 스프링이 팩토리로부터 자동으로 entity manger를 주입해준다.
	private EntityManager em;
	
	@Transactional
	public Long save(Member member) {
		em.persist(member);
		
		return member.getId(); // 커맨드와 쿼리를 분리? 사이드 이팩트 제거를 위해 return을 거의 없도록 설계한다함
	}
	
	public Member find(Long id) {
		return em.find(Member.class, id);
	}
}
