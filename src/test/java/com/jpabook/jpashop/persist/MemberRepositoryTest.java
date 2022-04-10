package com.jpabook.jpashop.persist;

import static org.assertj.core.api.Assertions.assertThat;

import com.jpabook.jpashop.entity.Member;
import com.jpabook.jpashop.persist.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class MemberRepositoryTest {
	@Autowired
	private MemberRepository memberRepository;
	
	@Test
	@DisplayName("멤버 정보 저장 테스트")
	@Transactional
	@Rollback(true)
	public void saveTest() throws Exception {
	    // * given
		String memberName = "memberA";
		Member member = new Member();
		member.setName(memberName);
		
		// * when
		final Long id = memberRepository.save(member);
		final Member findMember = memberRepository.find(id);
		
		// * then
		assertThat(id).isGreaterThan(0L);
		assertThat(member).isEqualTo(findMember);
		assertThat(member).isSameAs(findMember);
		assertThat(member.getId()).isEqualTo(id);
		assertThat(member.getName()).isEqualTo("dndndndn");
	}
}