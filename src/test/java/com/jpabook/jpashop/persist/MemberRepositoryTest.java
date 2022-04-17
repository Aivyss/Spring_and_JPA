package com.jpabook.jpashop.persist;

import static org.assertj.core.api.Assertions.assertThat;

import com.jpabook.jpashop.domain.common.Address;
import com.jpabook.jpashop.domain.common.DeletedFlag;
import com.jpabook.jpashop.domain.common.Edits;
import com.jpabook.jpashop.domain.member.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("RedundantThrows")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class MemberRepositoryTest {
	@Autowired
	private MemberRepository memberRepository;
	
	@Test
	@DisplayName("멤버 정보 저장 및 조회")
	public void testSaveAndFind() throws Exception {
	    // * given
		String memberName = "memberA";
		String memberNickname = "nicknameA";
		Member member = new Member(
			null,
			memberName,
			memberNickname,
			new Address("city", "street", "10-1010"),
			new Edits(LocalDateTime.now(), DeletedFlag.N,  null)
		);
		
		// * when
		final Long id = memberRepository.save(member);
		final Member findMember = memberRepository.findOne(id);
		
		// * then
		assertThat(id).isGreaterThan(0L);
		assertThat(member).isEqualTo(findMember);
		assertThat(member).isSameAs(findMember);
		assertThat(member.getId()).isEqualTo(id);
		assertThat(member.getName()).isEqualTo(memberName);
		assertThat(member.getNickname()).isEqualTo(memberNickname);
		assertThat(findMember.getId()).isEqualTo(id);
		assertThat(findMember.getName()).isEqualTo(memberName);
		assertThat(findMember.getNickname()).isEqualTo(memberNickname);
	}
	
	@Test
	@DisplayName("전체 유저 조회")
	public void testFindAll() throws Exception {
	    // * given
		final List<Member> inputList = new ArrayList<>();
		for (int i = 0; i < 5; i += 1) {
			Member member = new Member(
				null,
				i + "",
				i + "",
				new Address("city", "street", "10-1010"),
				new Edits(LocalDateTime.now(), DeletedFlag.N,  null)
			);
			inputList.add(member);
			memberRepository.save(member);
		}
	    
	    // * when
		final List<Member> allMembers = memberRepository.findAll();
		
		// * then
		final List<Long> actualIdList = allMembers.stream().map(Member::getId)
			.collect(Collectors.toList());
		final List<Long> expectedIdList = inputList.stream().map(Member::getId)
			.collect(Collectors.toList());
		
		for (Long expectedId : expectedIdList) {
			assertThat(actualIdList.contains(expectedId)).isTrue();
		}
	}
	
	@Test
	@DisplayName("이름으로 조회")
	public void testFindByName() throws Exception {
	    // * given
		String partial = "^^^^";
		Member memberA = new Member(
			null,
			"김^^^^수",
			"nicknameA",
			new Address("city", "street", "10-1010"),
			new Edits(LocalDateTime.now(), DeletedFlag.N,  null)
		);
		Member memberB = new Member(
			null,
			"이^^^^구",
			"nicknameB",
			new Address("city", "street", "10-1010"),
			new Edits(LocalDateTime.now(), DeletedFlag.N,  null)
		);
		Member memberC = new Member(
			null,
			"무관계 데이터",
			"nicknameC",
			new Address("city", "street", "10-1010"),
			new Edits(LocalDateTime.now(), DeletedFlag.N,  null)
		);
		memberRepository.save(memberA);
		memberRepository.save(memberB);
		memberRepository.save(memberC);
	    
	    // * when
		final List<Member> findMembers = memberRepository.findByName(partial);
		
		// * then
		assertThat(findMembers.size()).isEqualTo(2);
		assertThat(findMembers.contains(memberA)).isTrue();
		assertThat(findMembers.contains(memberB)).isTrue();
		
	}
	
	@Test
	@DisplayName("닉네임으로 유니크 조회")
	public void testFindByNickname() throws Exception {
	    // * given
		Member memberA = new Member(
			null,
			"김^^^^수",
			"nicknameA",
			new Address("city", "street", "10-1010"),
			new Edits(LocalDateTime.now(), DeletedFlag.N,  null)
		);
		Member memberB = new Member(
			null,
			"이^^^^구",
			"nicknameB",
			new Address("city", "street", "10-1010"),
			new Edits(LocalDateTime.now(), DeletedFlag.N,  null)
		);
		Member memberC = new Member(
			null,
			"무관계 데이터",
			"nicknameC",
			new Address("city", "street", "10-1010"),
			new Edits(LocalDateTime.now(), DeletedFlag.N,  null)
		);
		memberRepository.save(memberA);
		memberRepository.save(memberB);
		memberRepository.save(memberC);
		
	    // * when
		final Member findMember = memberRepository.findByNickname("nicknameA");
		
	    // * then
		assertThat(memberA).isEqualTo(findMember);
		assertThat(memberA.getId()).isEqualTo(findMember.getId());
	}
}