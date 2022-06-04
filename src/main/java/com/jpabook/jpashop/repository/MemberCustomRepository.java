package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.member.Member;
import java.util.List;

public interface MemberCustomRepository {
	/**
	 * 이름을 이용한 유저 조회
	 *
	 * @param name 검색될 이름
	 * @return 이름에 의해 검색된 유저 리스트
	 */
	List<Member> findByName(String name);
	/**
	 * 유니크한 닉네임을 이용한 단건 조회
	 *
	 * @param nickname 검색할 닉네임
	 * @return 조회된 유저
	 */
	Member findByNickname(String nickname);
}
