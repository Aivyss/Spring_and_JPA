package com.jpabook.jpashop.domain.item;

import static com.jpabook.jpashop.domain.sequence.SequenceGenerators.ITEM_SEQ_GEN;
import static com.jpabook.jpashop.domain.sequence.Sequences.ITEM_SEQ;

import com.jpabook.jpashop.domain.category.Category;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@SequenceGenerator(name = ITEM_SEQ_GEN, sequenceName = ITEM_SEQ, initialValue = 1, allocationSize = 50)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "D_TYPE") // 모든 테이블에 넣으려면 구분자 타입컬럼이 필요하므로 그것을 설정
/**
 * <h1> 테이블 전략을 하나의 테이블에 다 집어넣는 것으로 선택하였음 </h1>
 * <ul>
 *     <li> SINGLE_TABLE: 하나의 테이블에 모든 클래스의 컬럼을 다 넣음 </li>
 *     <li> TABLE_PER_CLASS: 클래스 별로 테이블을 별도 생성함 </li>
 *     <li> JOINED: 조인관계를 이용해 공통부분을 조인해서 쓰도록 함 </li>
 * </ul>
 */
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
	
	@ManyToMany(mappedBy = "items")
	List<Category> categories = new ArrayList<>();
}
