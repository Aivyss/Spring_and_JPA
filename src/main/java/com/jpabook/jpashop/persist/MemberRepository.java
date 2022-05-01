package com.jpabook.jpashop.persist;

import com.jpabook.jpashop.domain.member.Member;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
	
	private final EntityManager em;
	
	@Autowired
	public MemberRepository(EntityManager em) {
		this.em = em;
		/*
		 <ul>
		     <li> @PersistenceContext 어노테이션을 이용한 주입  </li>
		     <li> @Autowired를 이용해도 된다.</li>
		     <li>
		        스프링이 팩토리로부터 자동으로 entity manger를 주입해준다.
		        (org.springframework.boot:spring-boot-starter-data-jpa)
		     </li>
		     <li> Spring-boot 환경이 아니라면 표준 어노테이션인 @PersistenceContext를 이용해야만 함 </li>
		 </ul>
		 */
	}
	
	/**
	 * 유저 단건 저장
	 *
	 * @param member 저장될 entity instance
	 * @return 영속화 된 객체에 부여된 기본키
	 */
	public Long save(Member member) {
		em.persist(member);
		
		return member.getId(); // 커맨드와 쿼리를 분리? 사이드 이팩트 제거를 위해 return을 거의 없도록 설계한다함
	}
	
	/**
	 * 유저 단건 조회
	 *
	 * @param id 단건 조회를 위한 기본키
	 * @return 조회된 유저
	 */
	public Member findOne(Long id) {
		return em.find(Member.class, id);
	}
	
	/**
	 * 모든 유저 조회
	 *
	 * @return persist에 저장된 유저 전체 리스트
	 */
	public List<Member> findAll() {
		return em.createQuery("select m from Member m", Member.class).getResultList();
	}
	
	/**
	 * 이름을 이용한 유저 조회
	 *
	 * @param name 검색될 이름
	 * @return 이름에 의해 검색된 유저 리스트
	 */
	public List<Member> findByName(String name) {
		return em.createQuery(
			"select m from Member m where m.name like concat('%', :name, '%')",
			Member.class
		).setParameter("name", name).getResultList();
	}
	
	/**
	 * 유니크한 닉네임을 이용한 단건 조회
	 *
	 * @param nickname 검색할 닉네임
	 * @return 조회된 유저
	 */
	public Member findByNickname(String nickname) {
		return em.createQuery(
			"select m from Member m where m.nickname = :nickname",
			Member.class
		).setParameter("nickname", nickname).getSingleResult();
	}
}
