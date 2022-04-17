package com.jpabook.jpashop.service.member;

import com.jpabook.jpashop.domain.member.Member;
import java.util.List;

public interface MemberService {
	
	/**
	 * 회원가입 수행 메소드 정의
	 * @param member 회원 인스턴스
	 * @return 회원키
	 */
	Long signUp(Member member);
	
	/**
	 * 키를 이용한 회원 검색 메소드 정의
	 * @param memberId 회원 기본키
	 * @return 회원 인스턴스
	 */
	Member findOneMember(Long memberId);
	
	/**
	 * 회원 전체 검색 메소드 정의
	 * @return 회원 전체 리스트
	 */
	List<Member> findAllMembers();
}
