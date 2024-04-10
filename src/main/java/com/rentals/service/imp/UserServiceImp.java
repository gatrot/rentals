package com.rentals.service.imp;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rentals.entity.User;
import com.rentals.repository.UserRepository;
import com.rentals.service.UserService;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository repo;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	@Transactional(readOnly = false)
	public Boolean createUser(User user) {
		Boolean res = false;
		try {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user = repo.save(user);
			res = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public User findUserByEmail(String email) {
		return repo.getUserByEmail(email);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public User getUserById(UUID userId) {
		return repo.getUserById(userId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public User confirmRegistration(User user) {
		user.setEmailConfirmed();
		repo.save(user);
		return user;
	}
}
