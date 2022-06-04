package com.jpabook.jpashop.mvc.member;

import com.jpabook.jpashop.domain.common.Address;
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
	 * 기존 존재하는 회원의 정보 업데이트 메소드
	 * @param memberId 회원 identifier
	 * @param name 변경되는 회원 이름
	 * @param address 변경되는 회원의 주소정보
	 */
	void updateMember(long memberId, String name, Address address);
	
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
