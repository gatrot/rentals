package com.rentals.service.imp;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rentals.entity.User;
import com.rentals.repository.UserRepository;
import com.rentals.service.UserService;

/**
 * A Service-Implementation class for the {@link UserDetails} DAO-Layer service
 * 
 * @author Hoffman
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	/*
	 * Spring Dependency Injection
	 */
	@Autowired
	private UserService userService;

	/*
	 * Get User By user name from DB
	 * 
	 * @param username
	 * @param password
	 * @return {@link UserDetails}
	 */
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userService.findUserByEmail(email);
		if (user == null) {
			return null;
		}

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				grantedAuthorities);
	}

}