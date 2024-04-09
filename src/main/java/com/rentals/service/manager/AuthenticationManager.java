package com.rentals.service.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import com.rentals.entity.User;
import com.rentals.object.ResetPasswordRequest;
import com.rentals.object.UserDetailsDTO;
import com.rentals.object.WebResponse;
import com.rentals.service.UserService;
import com.rentals.service.imp.RegistrationValidatorServiceImp;

public class AuthenticationManager {

	@Autowired
	private RegistrationValidatorServiceImp regValidatorService;

	@Autowired
	private UserService userService;

	public WebResponse registration(UserDetailsDTO userDetail, BindingResult bindingResult) {
		regValidatorService.validate(userDetail, bindingResult);
		if (bindingResult.hasErrors()) {
			String error = regValidatorService.getError(bindingResult);
			return new WebResponse(false, HttpStatus.BAD_REQUEST, error);
		}

		User user = new User(userDetail.getEmail(), userDetail.getUsername(), userDetail.getPassword());
		Boolean userCreated = userService.createUser(user);
		if (!userCreated) {
			return new WebResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create user");
		}
		
		userDetail.setUserId(user.getId());
		login(userDetail);
		userDetail.setPassword(null);
		return new WebResponse(true, HttpStatus.CREATED, userDetail);
	}

	public WebResponse login(UserDetailsDTO userDetail) {
		// TODO Auto-generated method stub
		return null;
	}

	public WebResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
