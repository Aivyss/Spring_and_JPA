package com.jpabook.jpashop.domain.item.impl;

import com.jpabook.jpashop.domain.item.Item;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@DiscriminatorValue("ALBUM")
public class Album extends Item {
	
	@Column(name = "ARTIST")
	private String artist;
	@Column(name = "ETC")
	private String etc;
}
