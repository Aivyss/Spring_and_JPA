package com.jpabook.jpashop.mvc.item;

import static org.assertj.core.api.Assertions.assertThat;

import com.jpabook.jpashop.domain.common.Address;
import com.jpabook.jpashop.domain.common.DeletedFlag;
import com.jpabook.jpashop.domain.common.Edits;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.domain.item.impl.Album;
import com.jpabook.jpashop.domain.item.impl.Book;
import com.jpabook.jpashop.domain.item.impl.Movie;
import com.jpabook.jpashop.domain.member.Member;
import com.jpabook.jpashop.repository.MemberRepository;
import com.jpabook.jpashop.repository.ItemRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ItemRepositoryTest {
	
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private MemberRepository memberRepository;
	
	@Test
	@DisplayName("아이템 저장/조회 테스트")
	public void testSaveAndFindOne() {
		// * given
		String memberName = "memberA";
		String memberNickname = "nicknameA";
		Member member = new Member(
			null,
			memberName,
			memberNickname,
			new Address("city", "street", "10-1010"),
			new Edits(LocalDateTime.now(), DeletedFlag.N, null)
		);
		Edits edit = new Edits(LocalDateTime.now(), DeletedFlag.N, member);
		Album album = new Album(
			null, "album_test", 5, 10000,
			"aritst_name", null, edit
		);
		Book book = new Book(
			null, "book_name", 100, 5000,
			"author_name", "isbn", edit
		);
		Movie movie = new Movie(
			null, "movie_name", 10, 10000,
			"director_name", "actor_name", edit
		);
		
		// * when
		memberRepository.save(member);
		itemRepository.persistOrMerge(album);
		itemRepository.persistOrMerge(book);
		itemRepository.persistOrMerge(movie);
		
		final Item actualAlbum = itemRepository.findById(album.getId()).orElseThrow();
		final Item actualBook = itemRepository.findById(book.getId()).orElseThrow();
		final Item actualMovie = itemRepository.findById(movie.getId()).orElseThrow();
		
		// * then
		assertThat(member.getId()).isGreaterThan(0L);
		assertThat(album.getId()).isGreaterThan(0L);
		assertThat(book.getId()).isGreaterThan(0L);
		assertThat(movie.getId()).isGreaterThan(0L);
		assertThat(actualAlbum).isEqualTo(album);
		assertThat(actualBook).isEqualTo(book);
		assertThat(actualMovie).isEqualTo(movie);
	}
	
	@Test
	@DisplayName("아이템 리스트 조회")
	public void testFindAll() {
	    // * given
		String memberName = "memberA";
		String memberNickname = "nicknameA";
		Member member = new Member(
			null,
			memberName,
			memberNickname,
			new Address("city", "street", "10-1010"),
			new Edits(LocalDateTime.now(), DeletedFlag.N, null)
		);
		Edits edit = new Edits(LocalDateTime.now(), DeletedFlag.N, member);
		Album album = new Album(
			null, "album_test", 5, 10000,
			"aritst_name", null, edit
		);
		Book book = new Book(
			null, "book_name", 100, 5000,
			"author_name", "isbn", edit
		);
		Movie movie = new Movie(
			null, "movie_name", 10, 10000,
			"director_name", "actor_name", edit
		);
		
		memberRepository.save(member);
		itemRepository.persistOrMerge(album);
		itemRepository.persistOrMerge(book);
		itemRepository.persistOrMerge(movie);
		
		// * when
		final List<Item> all = itemRepository.findAll();
		
		// * then
		assertThat(all.contains(album)).isTrue();
		assertThat(all.contains(book)).isTrue();
		assertThat(all.contains(movie)).isTrue();
	}
}
