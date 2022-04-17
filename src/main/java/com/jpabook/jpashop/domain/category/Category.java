package com.jpabook.jpashop.domain.category;

import static com.jpabook.jpashop.domain.sequence.SequenceGenerators.CATEG_SEQ_GEN;
import static com.jpabook.jpashop.domain.sequence.Sequences.CATEG_SEQ;

import com.jpabook.jpashop.domain.common.Edits;
import com.jpabook.jpashop.domain.item.Item;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@SequenceGenerator(name = CATEG_SEQ_GEN, sequenceName = CATEG_SEQ, initialValue = 1, allocationSize = 50)
@SuppressWarnings({"JpaDataSourceORMInspection", "DefaultAnnotationParam", "unused"})
public class  Category {
	
	@Id
	@GeneratedValue(generator = CATEG_SEQ_GEN, strategy = GenerationType.SEQUENCE)
	@Column(name = "CATEGORY_ID")
	private Long id;
	
	@Column(name = "CATEGORY_NAME")
	private String name;
	
	@Embedded
	private Edits edits;
	
	@JoinColumn(name = "PARENT_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Category parent;
	
	@OneToMany(mappedBy = "parent")
	List<Category> children = new ArrayList<>();
	
	@JoinTable(
		name = "CATEGORY_ITEM_TABLE",
		joinColumns = @JoinColumn(name="CATEGORY_ID"),
		inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
	) // 다대다 관계는 두 관계를 이어주는 테이블을 정의해야만 연관관계를 맺을 수 있다.
	@ManyToMany
	List<Item> items = new ArrayList<>();
	
	// * relation util methods
	public void addChildCategory(Category child) {
		this.children.add(child);
		child.setParent(this);
	}
}
