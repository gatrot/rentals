package com.rentals.object;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDTO {
	private UUID adId;
	private String country;
	private String city;
	private String streetNameHebrew;
	private String streetNameEnglish;
	private String streetNum;

	public AddressDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddressDTO(UUID adId, String country, String city, String streetNameHebrew, String streetNameEnglish,
			String streetNum) {
		super();
		this.adId = adId;
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
