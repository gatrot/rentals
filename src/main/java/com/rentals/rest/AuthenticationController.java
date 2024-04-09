package com.rentals.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentals.object.ResetPasswordRequest;
import com.rentals.object.UserDetailsDTO;
import com.rentals.object.WebResponse;
import com.rentals.service.manager.AuthenticationManager;

@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authManager;

	@PostMapping("/registration")
	public ResponseEntity<WebResponse> registration(@RequestBody(required = true) UserDetailsDTO userDetail,
			BindingResult bindingResult) {
		WebResponse response = authManager.registration(userDetail, bindingResult);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<WebResponse> login(@RequestBody UserDetailsDTO userDetail) {
		WebResponse response = authManager.login(userDetail);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/reset-password")
	public ResponseEntity<WebResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
		WebResponse response = authManager.resetPassword(resetPasswordRequest);
		return ResponseEntity.ok().body(response);
	}
	
	//TODO LOGOUT
}
