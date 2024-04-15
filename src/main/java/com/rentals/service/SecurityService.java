package com.rentals.service;

import com.rentals.entity.User;

public interface SecurityService {

	public String findLoggedInUserEmail();

	public User login(String email, String password);
}
