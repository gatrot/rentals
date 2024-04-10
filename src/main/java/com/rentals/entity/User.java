package com.rentals.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private UUID id;
	@Column(unique = true)
	private String email;
	private Boolean emailConfirmed;
	private String username;
	private String password;
	@Column(name = "ads_published")
	@OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Advertisement> adsPublished;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_favorites", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "ad_id"))
	private List<Advertisement> favorites;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String email, String username, String password) {
		super();
		this.email = email;
		this.username = username;
		this.password = password;
		this.emailConfirmed = false;
		this.adsPublished = new ArrayList<Advertisement>();
		this.favorites = new ArrayList<Advertisement>();
	}

	public Boolean getEmailConfirmed() {
		return emailConfirmed;
	}

	public void setEmailConfirmed(Boolean emailConfirmed) {
		this.emailConfirmed = emailConfirmed;
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

	// Ads

	public List<Advertisement> getAdsPublished() {
		return adsPublished;
	}

	public void setAdsPublished(List<Advertisement> adsPublished) {
		this.adsPublished = adsPublished;
	}

	public List<Advertisement> getAdsFavorited() {
		return favorites;
	}

	public void setAdsFavorited(List<Advertisement> adsFavorited) {
		this.favorites = adsFavorited;
	}
}
