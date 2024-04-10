package com.rentals.service;

import com.rentals.entity.User;

public interface SecurityService {

	public String findLoggedInUsername();

	public User login(String email, String password);
}
