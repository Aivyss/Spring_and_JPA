package com.jpabook.jpashop.mvc.member;

import static com.jpabook.jpashop.exception.ExceptionCase.DUPLICATE_ROW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.jpabook.jpashop.domain.common.Address;
import com.jpabook.jpashop.domain.common.DeletedFlag;
import com.jpabook.jpashop.domain.common.Edits;
import com.jpabook.jpashop.domain.member.Member;
import com.jpabook.jpashop.exception.CommonError;
import com.jpabook.jpashop.exception.ExceptionSupplierUtils;
import com.jpabook.jpashop.interfaces.exceptions.JPAShopError;
import com.jpabook.jpashop.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
	@Mock
	private ExceptionSupplierUtils exceptions;
	
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
		when(exceptions.getExceptionInstance(DUPLICATE_ROW)).thenReturn(new CommonError(DUPLICATE_ROW));
		
		// * then
		assertThatThrownBy(()-> memberService.signUp(member)).isInstanceOf(JPAShopError.class)
			.isInstanceOf(CommonError.class);
	}
	
	@Test
	@DisplayName("정상 회원가입 케이스")
	public void testSaveNormalCase() throws Exception {
		// * given
		Long expectedId = 1L;
		String memberName = "memberA";
		String memberNickname = "nicknameA";
		Member member = new Member(
			expectedId,
			memberName,
			memberNickname,
			new Address("city", "street", "10-1010"),
			new Edits(LocalDateTime.now(), DeletedFlag.N,  null)
		);
		
		// * set stubs
		when(memberRepository.save(eq(member))).thenReturn(member);
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
		when(memberRepository.findById(eq(memberId))).thenReturn(Optional.ofNullable(expectedMember));
		
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