package com.jpabook.jpashop.domain.item;

import static com.jpabook.jpashop.domain.sequence.SequenceGenerators.ITEM_SEQ_GEN;
import static com.jpabook.jpashop.domain.sequence.Sequences.ITEM_SEQ;

import com.jpabook.jpashop.domain.category.Category;
import com.jpabook.jpashop.domain.common.Edits;
import com.jpabook.jpashop.exception.CommonError;
import com.jpabook.jpashop.exception.ExceptionCase;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import lombok.Getter;

/**
 * <h1> 테이블 전략을 하나의 테이블에 다 집어넣는 것으로 선택하였음 </h1>
 * <ul>
 *     <li> SINGLE_TABLE: 하나의 테이블에 모든 클래스의 컬럼을 다 넣음 </li>
 *     <li> TABLE_PER_CLASS: 클래스 별로 테이블을 별도 생성함 </li>
 *     <li> JOINED: 조인관계를 이용해 공통부분을 조인해서 쓰도록 함 </li>
 * </ul>
 */
@Entity
@Getter
@SequenceGenerator(name = ITEM_SEQ_GEN, sequenceName = ITEM_SEQ, initialValue = 1, allocationSize = 50)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "D_TYPE") // 모든 테이블에 넣으려면 구분자 타입컬럼이 필요하므로 그것을 설정
@SuppressWarnings({"JpaDataSourceORMInspection", "DefaultAnnotationParam", "unused",
	"FieldMayBeFinal"})
public abstract class Item {
	
	@Id
	@GeneratedValue(generator = ITEM_SEQ_GEN, strategy = GenerationType.SEQUENCE)
	@Column(name = "ITEM_ID")
	private Long id;
	
	@Column(name = "ITEM_NAME")
	private String name;
	
	@Column(name = "STOCK_QUANTITY")
	private int stockQuantity;
	
	@Column(name = "PRICE")
	private int price;
	
	@Embedded
	private Edits edits;
	
	@ManyToMany(mappedBy = "items")
	private List<Category> categories = new ArrayList<>();
	
	public Item(Long id, String name, int stockQuantity, int price, Edits edit) {
		this.id = id;
		this.name = name;
		this.stockQuantity = stockQuantity;
		this.price = price;
		this.edits = edit;
	}
	
	// * default constructor for JPA
	public Item() {}
	
	// * relation util methods
	public void addCategory (Category category) {
		this.categories.add(category);
		category.getItems().add(this);
	}
	
	// * business logics (재고수량 처리에 관련한 비즈니스 로직)
	public void addStock(int quantity) {
		this.stockQuantity += quantity;
	}
	
	public void removeStock(int quantity) {
		if (this.stockQuantity - quantity < 0) {
			throw new CommonError(ExceptionCase.NOT_ENOUGH_STOCK, "002", "EXCEPTION.NOT_ENOUGH_STOCK");
		} else {
			this.stockQuantity -= quantity;
		}
	}
}
