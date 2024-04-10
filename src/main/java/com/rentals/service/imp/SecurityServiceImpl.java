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

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	private static final Logger logger = LogManager.getLogger(SecurityServiceImpl.class);

	@Override
	public String findLoggedInUsername() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
		if (userDetails instanceof UserDetails) {
			return ((UserDetails) userDetails).getUsername();
		}
		return null;
	}

	@Override
	public User login(String email, String password) {
		try {
			UserDetails userDetails = new org.springframework.security.core.userdetails.User(email, password,
					new HashSet<GrantedAuthority>());
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, password, userDetails.getAuthorities());

			authenticationManager.authenticate(usernamePasswordAuthenticationToken);

			if (usernamePasswordAuthenticationToken.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				logger.info("Successful login. email: " + email + " | password: " + password);
				return userService.findUserByEmail(email);
			}
			logger.error("Credentials incorrect. email:" + email + " | password: " + password);
		} catch (BadCredentialsException bc) {
			logger.error("Credentials incorrect. email:" + email + " | password: " + password);
		} catch (UsernameNotFoundException unf) {
			logger.error("User does not exist. email:" + email + " | password: " + password);
		} catch (Exception e) {
			logger.error("An error occurred while trying to login");
			e.printStackTrace();
		}
		return null;
	}
}
