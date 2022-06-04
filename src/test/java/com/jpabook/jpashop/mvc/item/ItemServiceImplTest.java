package com.jpabook.jpashop.mvc.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.exception.ExceptionSupplierUtils;
import com.jpabook.jpashop.repository.ItemRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@SuppressWarnings("unchecked")
@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {
	@Mock
	private ItemRepository repository;
	@Mock
	private ExceptionSupplierUtils exceptions;
	
	@InjectMocks
	private ItemServiceImpl service;
	
	@Test
	@DisplayName("아이템 저장 서비스 테스트")
	public void testSave() {
	    // * given
		final Item mock = mock(Item.class);
		
		// * set stubs
		doNothing().when(repository).persistOrMerge(mock);
		
		// * when
		service.save(mock);
	}
	
	@Test
	@DisplayName("전 아이템 조회 서비스 테스트")
	public void testFindItems() {
	    // * given
		List<Item> expected = mock(List.class);
	 
		// * set stubs
		when(repository.findAll()).thenReturn(expected);
		
	    // * when
		final List<Item> actual = service.findItems();
		
		// * then
		assertThat(actual).isSameAs(expected);
	}
	
	@Test
	public void testFindOne() {
	    // * given
		long id = 12415L;
		
		// * set sutbs
		Item expected = mock(Item.class);
		when(repository.findById(eq(id))).thenReturn(Optional.ofNullable(expected));
	    
	    // * when
		final Item actual = service.findOne(id);
		
		// * then
		assertThat(actual).isSameAs(expected);
	}
}
