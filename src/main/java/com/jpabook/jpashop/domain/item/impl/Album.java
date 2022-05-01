package com.jpabook.jpashop.domain.item.impl;

import com.jpabook.jpashop.domain.common.Edits;
import com.jpabook.jpashop.domain.item.Item;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("ALBUM")
@SuppressWarnings("JpaDataSourceORMInspection")
public class Album extends Item {
	
	@Column(name = "ARTIST")
	private String artist;
	@Column(name = "ETC")
	private String etc;
	
	public Album(Long id, String name, int stockQuantity, int price, String artist, String etc, Edits edit) {
		super(id, name, stockQuantity, price, edit);
		this.artist = artist;
		this.etc = etc;
	}
	
	// * default constructor for JPA
	public Album() {
	
	}
}
