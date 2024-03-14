package com.rentals.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.rentals.object.AccommodationType;

@Entity
@Table(name = "ADS")
public class Advertisement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ad_id")
	private UUID id;
	@Column(name = "date_added")
	private Date dateAdded;
	@OneToOne(mappedBy = "adId", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@PrimaryKeyJoinColumn
	private Address address; //ad id is the foreign primary key in addresses table
	private int rooms;
	private String description;
	private int price;
	private int floor;
	private int space;
	@Enumerated(EnumType.STRING)
	@Column(name = "acc_type")
	private AccommodationType accType;
	private Date availability;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UUID userId;
	@Column(name = "user_name")
	private String userName;
	private String phone;
	@OneToMany(mappedBy = "adId", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Image> images;
	private Boolean garage;
	private Boolean parking;
	private Boolean elevator;
	private Boolean balcony;
	private Boolean furnished;
	private Boolean pets;
	private Date renewal;
	
	public Advertisement(UUID id, Date dateAdded, Address address, int rooms, String description, int price, int floor,
			int space, AccommodationType accType, Date availability, UUID userId, String userName, String phone,
			List<Image> images, Boolean garage, Boolean parking, Boolean elevator, Boolean balcony, Boolean furnished,
			Boolean pets, Date renewal) {
		super();
		this.id = id;
		this.dateAdded = dateAdded;
		this.address = address;
		this.rooms = rooms;
		this.description = description;
		this.price = price;
		this.floor = floor;
		this.space = space;
		this.accType = accType;
		this.availability = availability;
		this.userId = userId;
		this.userName = userName;
		this.phone = phone;
		this.images = images;
		this.garage = garage;
		this.parking = parking;
		this.elevator = elevator;
		this.balcony = balcony;
		this.furnished = furnished;
		this.pets = pets;
		this.renewal = renewal;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getRooms() {
		return rooms;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getSpace() {
		return space;
	}

	public void setSpace(int space) {
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

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
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

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
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

	public Date getRenewal() {
		return renewal;
	}

	public void setRenewal(Date renewal) {
		this.renewal = renewal;
	}

	
}
