package com.rentals.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ADDRESSES")
public class Address {
	@Id
	@Column(name = "ad_id")
	private UUID adId;
	@MapsId // copy ad id
	@OneToOne
	@JoinColumn(name = "ad_id")
	@JsonIgnore
	private Advertisement ad;
	private String country;
	private String city;
	private String street;
	private String streetNum;

	public Address() {
		// TODO Auto-generated constructor stub
	}

	public Address(String country, String city, String street, String streetNum) {
		super();
		this.country = country;
		this.city = city;
		this.street = street;
		this.streetNum = streetNum;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNum() {
		return streetNum;
	}

	public void setStreetNum(String streetNum) {
		this.streetNum = streetNum;
	}

	public UUID getAdId() {
		return adId;
	}

	public void setAdId(UUID adId) {
		this.adId = adId;
	}

	public Advertisement getAd() {
		return ad;
	}

	public void setAd(Advertisement ad) {
		this.ad = ad;
	}

	
}
