package com.rentals.object;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvertisementDTO extends ResponseBodyBase {
	private UUID id;
	private AddressDTO address;
	private Integer rooms;
	private String description;
	private Integer price;
	private Integer floor;
	private Integer space;
	@JsonProperty("date_added")
	private Date dateAdded;
	@JsonProperty("acc_type")
	private AccommodationType accType;
	private Date availability;
	@JsonProperty("user_name")
	private String userName;
	private String phone;
	private List<ImageDTO> images;
	private Boolean garage;
	private Boolean parking;
	private Boolean elevator;
	private Boolean balcony;
	private Boolean furnished;
	private Boolean pets;
	private Date renewal;

	public AdvertisementDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdvertisementDTO(AddressDTO address, Integer rooms, String description, Integer price, Integer floor,
			Integer space, AccommodationType accType, Date availability, String userName, String phone,
			List<ImageDTO> images, Boolean garage, Boolean parking, Boolean elevator, Boolean balcony,
			Boolean furnished, Boolean pets, Date dateAdded, Date renewal) {
		super();
		this.address = address;
		this.rooms = rooms;
		this.description = description;
		this.price = price;
		this.floor = floor;
		this.space = space;
		this.accType = accType;
		this.availability = availability;
		this.userName = userName;
		this.phone = phone;
		this.images = images;
		this.garage = garage;
		this.parking = parking;
		this.elevator = elevator;
		this.balcony = balcony;
		this.furnished = furnished;
		this.pets = pets;
		this.dateAdded = 	dateAdded;
		this.renewal = 	renewal;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public Integer getRooms() {
		return rooms;
	}

	public void setRooms(Integer rooms) {
		this.rooms = rooms;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Integer getSpace() {
		return space;
	}

	public void setSpace(Integer space) {
		this.space = space;
	}

	public AccommodationType getAccType() {
		return accType;
	}

	public void setAccType(AccommodationType accType) {
		this.accType = accType;
	}

	public Date getAvailability() {
		return availability;
	}

	public void setAvailability(Date availability) {
		this.availability = availability;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<ImageDTO> getImages() {
		return images;
	}

	public void setImages(List<ImageDTO> images) {
		this.images = images;
	}

	public Boolean getGarage() {
		return garage;
	}

	public void setGarage(Boolean garage) {
		this.garage = garage;
	}

	public Boolean getParking() {
		return parking;
	}

	public void setParking(Boolean parking) {
		this.parking = parking;
	}

	public Boolean getElevator() {
		return elevator;
	}

	public void setElevator(Boolean elevator) {
		this.elevator = elevator;
	}

	public Boolean getBalcony() {
		return balcony;
	}

	public void setBalcony(Boolean balcony) {
		this.balcony = balcony;
	}

	public Boolean getFurnished() {
		return furnished;
	}

	public void setFurnished(Boolean furnished) {
		this.furnished = furnished;
	}

	public Boolean getPets() {
		return pets;
	}

	public void setPets(Boolean pets) {
		this.pets = pets;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Date getRenewal() {
		return renewal;
	}

	public void setRenewal(Date renewal) {
		this.renewal = renewal;
	}
	
	
	
	

}
