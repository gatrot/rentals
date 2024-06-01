package com.rentals.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "IMAGE")
public class Image {
	@Id
	@Column(unique = true)
	private String url;
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	private Advertisement ad;

	public Image() {
		// TODO Auto-generated constructor stub
	}

	public Image(String url, Advertisement ad) {
		super();
		this.url = url;
		this.ad = ad; 
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Advertisement getAd() {
		return ad;
	}

	public void setAd(Advertisement adId) {
		this.ad = adId;
	}

	

}
