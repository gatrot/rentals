package com.rentals.service;

import java.util.List;
import java.util.UUID;

import com.rentals.entity.User;

public interface UserService {

	Boolean createUser(User user);
	
	User findUserByEmail(String email);
		
	User getUserById(UUID userId);
	
	User confirmRegistration(User user);
	
	List<User> getAllUsers();
}
