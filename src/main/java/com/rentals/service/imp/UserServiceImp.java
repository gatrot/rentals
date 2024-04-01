package com.rentals.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rentals.entity.User;
import com.rentals.repository.UserRepository;
import com.rentals.service.UserService;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository repo;

	@Override
	public Boolean createUser(User user) {
		Boolean res = false;

		try {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			res = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

}
