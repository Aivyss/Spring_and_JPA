package com.jpabook.jpashop.mvc.member;

import static com.jpabook.jpashop.exception.ExceptionCase.DUPLICATE_ROW;
import static com.jpabook.jpashop.exception.ExceptionCase.NOT_FOUND_DATA;

import com.jpabook.jpashop.domain.common.Address;
import com.jpabook.jpashop.domain.member.Member;
import com.jpabook.jpashop.exception.ExceptionSupplierUtils;
import com.jpabook.jpashop.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <ul>
 *     <li> member service instance </li>
 *     <li> readOnly: 조회 전용으로 최적화 하도록 함. signUp은 persist 과정으로 따로 설정해야함 </li>
 * </ul>
 *
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	private final MemberRepository memberRepository;
	private final ExceptionSupplierUtils exceptions;
	
	/**
	 *  <ul>
	 *      <li> 회원가입을 수행하는 서비스 로직 </li>
	 *      <li> 중복 회원 방지 로직 </li>
	 *  </ul>
	 *
	 * @param member member instance for signup
	 * @return member primary key
	 */
	@Override
	@Transactional // 읽기가 아닌 write 작업이 들어가므로 annotation override
	public Long signUp(Member member) {
		findDuplicateMember(member);
		return memberRepository.save(member).getId();
	}
	
	@Override
	@Transactional
	public void updateMember(long memberId, String name, Address address) {
		final Member member = memberRepository.findById(memberId).orElseThrow();
		member.update(name, address);
	}
	
	/**
	 * 단건조회 서비스 메소드
	 * @param memberId member primary key
	 * @return selected member instance
	 */
	@Override
	public Member findOneMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(exceptions.getExceptionSupplier(NOT_FOUND_DATA));
	}
	
	/**
	 * 전체 조회 서비스 메소드
	 * @return all member list
	 */
	@Override
	public List<Member> findAllMembers() {
		return memberRepository.findAll();
	}
	
	// * service private util methods
	private void findDuplicateMember(Member member) {
		final Member findMember = memberRepository.findByNickname(member.getNickname());
		if (findMember != null) {
			throw exceptions.getExceptionInstance(DUPLICATE_ROW);
		}
	}
}
