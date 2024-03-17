package com.rentals.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "IMAGES")
public class Image {
	@Id
	@Column(unique=true)
	private String url;
	@ManyToOne(fetch = FetchType.EAGER)
	private Advertisement adId;
	
	public Image() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Image(String url) {
		super();
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Advertisement getAdId() {
		return adId;
	}
	public void setAdId(Advertisement adId) {
		this.adId = adId;
	}
	
	
}
