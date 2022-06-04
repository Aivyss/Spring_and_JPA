package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.member.Member;
import com.jpabook.jpashop.domain.member.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.query.JpaQueryMethodFactory;
import org.springframework.stereotype.Component;

/**
 * <ul>
 *     <li> MEMBER Table access class </li>
 * </ul>
 */
@Component
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {
	
	private final JPAQueryFactory queryFactory;
	
	@Override
	public List<Member> findByName(String name) {
		QMember member = QMember.member;
		
		return queryFactory.selectFrom(member)
			.where(member.name.like("%" + name + "%"))
			.fetch();
	}
	
	@Override
	public Member findByNickname(String nickname) {
		QMember member = QMember.member;
		
		return queryFactory.selectFrom(member)
			.where(member.nickname.eq(nickname))
			.fetchOne();
	}
}
