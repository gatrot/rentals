package com.rentals.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rentals.object.ResetPasswordRequest;
import com.rentals.object.UserDetailsDTO;
import com.rentals.object.WebResponse;
import com.rentals.service.manager.RentalAuthManager;

@RestController()
public class UserController {

	@Autowired
	private RentalAuthManager authManager;

	@PostMapping("public/registration")
	public ResponseEntity<WebResponse> registration(@RequestBody(required = true) UserDetailsDTO userDetailsDTO,
			BindingResult bindingResult) {
		WebResponse response = authManager.registration(userDetailsDTO, bindingResult);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("public/login")
	public ResponseEntity<WebResponse> login(@RequestBody UserDetailsDTO userDetailsDTO) {
		WebResponse response = authManager.login(userDetailsDTO);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("public/reset-password")
	public ResponseEntity<WebResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
		WebResponse response = authManager.resetPassword(resetPasswordRequest);
		return ResponseEntity.ok().body(response);
	}

	// logout is provided automatically by spring security
}
