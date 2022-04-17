package com.jpabook.jpashop.persist;

import static org.assertj.core.api.Assertions.assertThat;

import com.jpabook.jpashop.domain.common.Address;
import com.jpabook.jpashop.domain.common.DeletedFlag;
import com.jpabook.jpashop.domain.common.Edits;
import com.jpabook.jpashop.domain.member.Member;
import java.time.LocalDateTime;
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
	@Rollback(false)
	public void testSaveAndFind() throws Exception {
	    // * given
		String memberName = "memberA";
		Member member = new Member();
		member.setName(memberName);
		member.setAddress(new Address("city", "street", "10-1010"));
		member.setEdits(new Edits(LocalDateTime.now(), DeletedFlag.N,  null));
		
		// * when
		final Long id = memberRepository.save(member);
		final Member findMember = memberRepository.find(id);
		
		// * then
		assertThat(id).isGreaterThan(0L);
		assertThat(member).isEqualTo(findMember);
		assertThat(member).isSameAs(findMember);
		assertThat(member.getId()).isEqualTo(id);
		assertThat(member.getName()).isEqualTo(memberName);
		assertThat(findMember.getId()).isEqualTo(id);
		assertThat(findMember.getName()).isEqualTo(memberName);
	}
}