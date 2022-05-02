package com.jpabook.jpashop.domain.common;

import com.jpabook.jpashop.domain.member.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * <ul>
 *     <li> edit informations </li>
 *     <li> EDIT_USER_ID </li>
 *     <li> INPUT_TIME </li>
 *     <li> DELETED </li>
 * </ul>
 */
@Embeddable
@Getter
@Setter
@SuppressWarnings("JpaDataSourceORMInspection")

public class Edits {
	
	@Column(name = "INPUT_TIME")
	private LocalDateTime inputTime;
	
	@Column(name = "DELETED")
	private DeletedFlag deleted;
	
	@JoinColumn(name = "EDIT_MEMBER_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;
	
	protected Edits() {}
	
	public Edits(LocalDateTime inputTime, DeletedFlag deleted,
		Member member) {
		this.inputTime = inputTime;
		this.deleted = deleted;
		this.member = member;
	}
}
