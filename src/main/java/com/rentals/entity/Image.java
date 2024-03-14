package com.rentals.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "IMAGES")
public class Image {
	@Column(unique=true)
	private String url;
	@ManyToOne(fetch = FetchType.EAGER)
	private Advertisement adId;
}
