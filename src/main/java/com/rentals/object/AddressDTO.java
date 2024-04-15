package com.rentals.object;

import java.util.UUID;

import com.rentals.entity.Advertisement;

public class AddressDTO {
	private UUID adId;
	private String country;
	private String city;
	private String street;
	private String streetNum;

	public AddressDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddressDTO(UUID adId, String country, String city, String street, String streetNum) {
		super();
		this.adId = adId;
		this.country = country;
		this.city = city;
		this.street = street;
		this.streetNum = streetNum;
	}

	public UUID getAdId() {
		return adId;
	}

	public void setAdId(UUID adId) {
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

}
