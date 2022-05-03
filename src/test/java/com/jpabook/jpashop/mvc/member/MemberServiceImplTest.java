package com.jpabook.jpashop.mvc.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.jpabook.jpashop.domain.common.Address;
import com.jpabook.jpashop.domain.common.DeletedFlag;
import com.jpabook.jpashop.domain.common.Edits;
import com.jpabook.jpashop.domain.member.Member;
import com.jpabook.jpashop.exception.DuplicateRowException;
import com.jpabook.jpashop.interfaces.exceptions.JPAShopError;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@SuppressWarnings({"RedundantThrows", "unchecked"})
@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {
	@Mock
	private MemberRepository memberRepository;
	
	@InjectMocks
	private MemberServiceImpl memberService;
	
	@Test
	@DisplayName("중복된 회원 회원가입 케이스")
	public void testSaveDuplicatedCase() throws Exception {
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
		
		// * set stubs
		when(memberRepository.findByNickname(eq(memberNickname))).thenReturn(member);
		
		// * then
		assertThatThrownBy(()-> memberService.signUp(member)).isInstanceOf(JPAShopError.class).isInstanceOf(DuplicateRowException.class)
			.hasMessage("이미 존재하는 회원입니다.");
	}
	
	@Test
	@DisplayName("정상 회원가입 케이스")
	public void testSaveNormalCase() throws Exception {
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
		
		// * set stubs
		Long expectedId = 1L;
		when(memberRepository.save(eq(member))).thenReturn(expectedId);
		when(memberRepository.findByNickname(eq(memberNickname))).thenReturn(null);
		
		// * when
		final Long actualId = memberService.signUp(member);
		
		// * then
		assertThat(actualId).isEqualTo(expectedId);
	}
	
	@Test
	@DisplayName("단건 유저조회")
	public void testFindOneMember() throws Exception {
	    // * given
	    Long memberId = 91234L;
		
		// * set stubs
		final Member expectedMember = mock(Member.class);
		when(memberRepository.findOne(eq(memberId))).thenReturn(expectedMember);
		
	    // * when
		final Member findMember = memberService.findOneMember(memberId);
		
		// * then
		assertThat(findMember).isSameAs(expectedMember);
	}
	
	@Test
	@DisplayName("전체 유저조회")
	public void testFindAllMembers() throws Exception {
	    // * set stubs
		List<Member> allMembers = mock(List.class);
		when(memberRepository.findAll()).thenReturn(allMembers);
	    
	    // * when
		final List<Member> actualAllMembers = memberService.findAllMembers();
		
		// * then
		assertThat(actualAllMembers).isEqualTo(allMembers);
	}
}