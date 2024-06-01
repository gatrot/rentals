package com.rentals.model;

import java.util.UUID;

public class UserDetailsDTO extends ResponseBodyBase {
	private String email;
	private String username;
	private String password;
	private UUID id;
	private Boolean emailConfirmed;

	public UserDetailsDTO() {
	}

	public UserDetailsDTO(String email, String username, String password, UUID id, Boolean emailConfirmed) {
		super();
		this.email = email;
		this.username = username;
		this.password = password;
		this.id = id;
		this.emailConfirmed = emailConfirmed;
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

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Boolean getEmailConfirmed() {
		return emailConfirmed;
	}

	public void setEmailConfirmed(Boolean emailConfirmed) {
		this.emailConfirmed = emailConfirmed;
	}
	
	
}
