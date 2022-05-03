package com.jpabook.jpashop.mvc.member;

import com.jpabook.jpashop.domain.member.Member;
import com.jpabook.jpashop.exception.DuplicateRowException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MemberServiceImpl implements MemberService {
	
	private final MemberRepository memberRepository;
	
	@Autowired
	public MemberServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
		/*
			lombok의 @RequiredArgsConstructor를 이용하면 단일 생성자일 때
			@Autowired 없이도 dependency injection를 수행한다는 스프링의 기본 처리과정에 의해서
			생성자 자체를 정의해 줄 필요가 없다.
			
			그러나 명시적으로 이 부분은 코드로 나타내는 것을 선호하므로 직접 컨스트럭터를 정의하고
			@Autowired를 이용하였다.
		 */
	}
	
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
		return memberRepository.save(member);
	}
	
	/**
	 * 단건조회 서비스 메소드
	 * @param memberId member primary key
	 * @return selected member instance
	 */
	@Override
	public Member findOneMember(Long memberId) {
		return memberRepository.findOne(memberId);
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
			throw new DuplicateRowException("001-001", "이미 존재하는 회원입니다.");
		}
	}
}
