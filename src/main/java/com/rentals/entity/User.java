package com.rentals.entity;

import java.util.UUID;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "user_details")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private String email;
	private String username;
	private String password;
	private List<Advertisement> adsPublished;
	private List<Advertisement> adsFavorited;
	
	public User(String email, String username, String password) {
		super();
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	public UUID getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@OneToMany(
			mappedBy		= "user_details",
			cascade			= CascadeType.ALL,
			fetch			= FetchType.LAZY,
			orphanRemoval	= true)
	public List<Advertisement> getAdsPublished() {
		return adsPublished;
	}

	public void setAdsPublished(List<Advertisement> adsPublished) {
		this.adsPublished = adsPublished;
	}

	@ManyToMany
	@JoinTable(
			  name 				= "favorited_ads", 
			  joinColumns 		= @JoinColumn(name = "user_id"), 
			  inverseJoinColumns= @JoinColumn(name = "ad_id"))
	public List<Advertisement> getAdsFavorited() {
		return adsFavorited;
	}

	public void setAdsFavorited(List<Advertisement> adsFavorited) {
		this.adsFavorited = adsFavorited;
	}
}
