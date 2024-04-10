package com.rentals.service;

import java.util.UUID;

import com.rentals.entity.User;

public interface UserService {

	Boolean createUser(User user);

	User findUserByEmail(String email);

	User getUserById(UUID userId);

	User confirmRegistration(User user);
}
