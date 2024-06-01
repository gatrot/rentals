package com.rentals.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rentals.model.ResetPasswordRequest;
import com.rentals.model.UserDetailsDTO;
import com.rentals.model.WebResponse;
import com.rentals.service.manager.RentalsManager;

@RestController()
public class UserController {

	@Autowired
	private RentalsManager authManager;

	@PostMapping("public/registration")
	public ResponseEntity<WebResponse> registration(@RequestBody(required = true) UserDetailsDTO userDetailsDTO,
			BindingResult bindingResult, HttpServletRequest req) {
		WebResponse response = authManager.registration(userDetailsDTO, bindingResult, req);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("public/login")
	public ResponseEntity<WebResponse> login(@RequestBody(required = true) UserDetailsDTO userDetailsDTO) {
		WebResponse response = authManager.login(userDetailsDTO);
		return ResponseEntity.ok().body(response);

	}
	
	// logout is provided automatically by spring security

	@PostMapping("public/reset-password")
	public ResponseEntity<WebResponse> resetPassword(@RequestBody(required = true)  ResetPasswordRequest resetPasswordRequest) {
		WebResponse response = authManager.resetPassword(resetPasswordRequest);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("public/confirmation-email")
	public ModelAndView confirmUserByEmail(@RequestParam String token) {
		WebResponse response = authManager.confirmEmail(token);
		return new ModelAndView("redirect:/"+response.getRedirectURL() +"?email=confirmed");
	}
}
