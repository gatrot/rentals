package com.rentals.service.manager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.rentals.entity.User;
import com.rentals.object.ResetPasswordRequest;
import com.rentals.object.UserDetailsDTO;
import com.rentals.object.WebResponse;
import com.rentals.service.SecurityService;
import com.rentals.service.UserService;
import com.rentals.service.imp.ValidatorServiceImp;

@Service
public class RentalAuthManager {

	@Autowired
	private ValidatorServiceImp regValidatorService;

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	public WebResponse registration(UserDetailsDTO userDetailsDTO, BindingResult bindingResult) {
		regValidatorService.validate(userDetailsDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			String error = regValidatorService.getError(bindingResult);
			return new WebResponse(false, HttpStatus.BAD_REQUEST, error);
		}

		User user = new User(userDetailsDTO.getEmail(), userDetailsDTO.getUsername(), userDetailsDTO.getPassword());
		Boolean userCreated = userService.createUser(user);
		if (!userCreated) {
			return new WebResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create user");
		}

		userDetailsDTO.setUserId(user.getId());
		login(userDetailsDTO);
		userDetailsDTO.setPassword(null);
		// Send Confirmation Email here
		return new WebResponse(true, HttpStatus.CREATED, Arrays.asList(userDetailsDTO));
	}

	public WebResponse login(UserDetailsDTO userDetailsDTO) {
		User user = securityService.login(userDetailsDTO.getEmail(), userDetailsDTO.getPassword());
		if (user != null) {
			userDetailsDTO.setUserId(user.getId());
			userDetailsDTO.setUsername(user.getUsername());
			userDetailsDTO.setPassword(null);
			return new WebResponse(true, HttpStatus.OK, Arrays.asList(userDetailsDTO));
		}
		return new WebResponse(false, HttpStatus.UNAUTHORIZED, "Username or password are incorrect.");
	}

	public WebResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
		return null;

	}

//	public WebResponse test(Authentication authentication) {
//		System.err.println(authentication);
//		List<User> users = userService.getAllUsers();
//		List<UserDetailsDTO> userDetailsDTOs 
//		= users
//		.stream()
//		.map(user -> new UserDetailsDTO(user.getEmail(), user.getUsername(), "Ah Ah Ah"))
//		.collect(Collectors.toList()) ;
//		return new WebResponse(true, HttpStatus.OK, userDetailsDTOs );
//
//	}

}
