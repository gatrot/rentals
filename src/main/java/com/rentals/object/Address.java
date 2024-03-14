package com.rentals.object;

public class Address {

	private String country;
	private String city;
	private String streetName;
	private int streetNum;
	
	public Address(String country, String city, String streetName, int streetNum) {
		super();
		this.country = country;
		this.city = city;
		this.streetName = streetName;
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

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public int getStreetNum() {
		return streetNum;
	}

	public void setStreetNum(int streetNum) {
		this.streetNum = streetNum;
	}
}
