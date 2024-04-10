package com.rentals.service.manager;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.rentals.entity.Advertisement;
import com.rentals.entity.User;
import com.rentals.object.EmailType;
import com.rentals.object.FilterDTO;
import com.rentals.object.ResetPasswordRequest;
import com.rentals.object.UserDetailsDTO;
import com.rentals.object.WebResponse;
import com.rentals.service.AdvertisementService;
import com.rentals.service.SecurityService;
import com.rentals.service.UserService;
import com.rentals.service.imp.AccessTokenServiceImp;
import com.rentals.service.imp.EmailServiceImpl;
import com.rentals.service.imp.ValidatorServiceImp;

@Service
public class RentalsManager {

	@Autowired
	private ValidatorServiceImp regValidatorService;

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private AdvertisementService advertisementService;

	@Autowired
	private AccessTokenServiceImp accessTokenServiceImp;

	@Autowired
	private EmailServiceImpl emailServiceImp;

	public Page<Advertisement> searchAdsByCriteria(List<FilterDTO> filterDTOList, int page, int size) {
		return advertisementService.searchAdsByCriteria(filterDTOList, page, size);
	}

	public WebResponse registration(UserDetailsDTO userDetailsDTO, BindingResult bindingResult,
			HttpServletRequest req) {
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

		if (!emailServiceImp.sendEmailTemplate(user.getEmail(), req, EmailType.CONFIRMATION_EMAIL)) {
			userService.deleteUser(user);
			return new WebResponse(false, HttpStatus.INTERNAL_SERVER_ERROR,
					"Failed to send confirmation email, rolling back transaction.");
		}

		userDetailsDTO.setUserId(user.getId());
		login(userDetailsDTO);
		userDetailsDTO.setPassword(null);
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

	public WebResponse confirmEmail(String token) {
		String email = accessTokenServiceImp.getUserEmailByToken(token);
		if (email != null) {
			User user = userService.findUserByEmail(email);
			if (user != null) {
				user.setEmailConfirmed(true);
				if (userService.updateUser(user)) {
					return new WebResponse(true, HttpStatus.PERMANENT_REDIRECT, "index.html");
				}
			}
		}
		return new WebResponse(false, HttpStatus.UNAUTHORIZED, "Incorrect token. Registry wasn't finished.");
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