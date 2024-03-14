package com.rentals.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESSES")
public class Address {
	private String country;
	private String city;
	private String street;
	private String streetNum;
	@OneToOne
	@MapsId //copy ad id
	@JoinColumn(name = "ad_id")
	private UUID adId; //ad's primary key (the ad id) is a foreign primary key in addresses table
	
	public Address(String country, String city, String street, String streetNum, UUID adId) {
		super();
		this.country = country;
		this.city = city;
		this.street = street;
		this.streetNum = streetNum;
		this.adId = adId;
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
	
}

