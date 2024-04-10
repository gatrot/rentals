package com.rentals.service.imp;

import java.util.HashSet;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.rentals.entity.User;
import com.rentals.service.SecurityService;
import com.rentals.service.UserService;


@Component
public class SecurityServiceImpl implements SecurityService {

	/*
	 * Spring dependency injection
	 */
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService ;



	private static final Logger logger = LogManager.getLogger(SecurityServiceImpl.class);

	/**
	 * This method is custom implementation of the spring security get admin portal
	 * user
	 */
	@Override
	public String findLoggedInUsername() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
		if (userDetails instanceof UserDetails) {
			return ((UserDetails) userDetails).getUsername();
		}
		return null;
	}

	/**
	 * This method is custom implementation of the spring security login method
	 */
	@Override
	public User login(String email, String password) {
		try {
			UserDetails userDetails = 
					new org.springframework.security.core.userdetails.User(email, password, new HashSet<GrantedAuthority>());
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

			authenticationManager.authenticate(usernamePasswordAuthenticationToken);

			if (usernamePasswordAuthenticationToken.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				logger.info("A new successful Login to Admin Portal, Credentials that was given : User Name: " + email + ", Password: " + password);
				return userService.findUserByEmail(email) ;
			}
			logger.error("User details are incorrect ,Credentials that was given : User Name:" + email + ", Password: " + password);
		} catch (BadCredentialsException bc) {
			logger.error("Access to Admin Portal was denied due to Bad Credentials, Credentials that was given : User Name: " + email + ", Password: " + password);
		} catch (UsernameNotFoundException unf) {
			logger.error("Access to Admin Portal was denied due to a non existed user, Credentials that was given : User Name: " + email + ", Password: " + password);
		} catch (Exception e) {
			logger.error("An error occurred while trying to login to the Admin Portal");
			e.printStackTrace();
		}
		return null;
	}
}
