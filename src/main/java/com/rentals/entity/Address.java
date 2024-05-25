package com.rentals.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ADDRESS")
public class Address {
	@Id
	@Column(name = "address_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID adId;
	@OneToOne(mappedBy = "address")
	private Advertisement ad;
	private String country;
	private String city;
	private String streetNameHebrew;
	private String streetNameEnglish;
	private String streetNum;

	public Address() {
		// TODO Auto-generated constructor stub
	}

	public Address(Advertisement ad, String country, String city, String streetNameHebrew, String streetNameEnglish,
			String streetNum) {
		super();
		this.ad = ad;
		this.country = country;
		this.city = city;
		this.streetNameHebrew = streetNameHebrew;
		this.streetNameEnglish = streetNameEnglish;
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

	public String getStreetNameHebrew() {
		return streetNameHebrew;
	}

	public void setStreetNameHebrew(String streetNameHebrew) {
		this.streetNameHebrew = streetNameHebrew;
	}

	public String getStreetNameEnglish() {
		return streetNameEnglish;
	}

	public void setStreetNameEnglish(String streetNameEnglish) {
		this.streetNameEnglish = streetNameEnglish;
	}

	public String getStreetNum() {
		return streetNum;
	}

	public void setStreetNum(String streetNum) {
		this.streetNum = streetNum;
	}

}
