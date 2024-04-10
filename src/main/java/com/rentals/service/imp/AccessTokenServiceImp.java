package com.rentals.service.imp;

import org.springframework.stereotype.Component;

import com.rentals.entity.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

@Component
public class AccessTokenServiceImp {
	private static final Logger log = Logger.getLogger(AccessTokenServiceImp.class.getSimpleName());

	// Access-Token registry
	Map<String, String> tokenRegister;

	public AccessTokenServiceImp() {
		tokenRegister = new ConcurrentHashMap<String, String>();
	}

	/**
	 * Method will validate if user have the same Access-Token that was send to them
	 * by email
	 */
	public boolean isEligible(User user, String token) {
		try {
			String tokenInMap = tokenRegister.get(user.getUsername());
			return token.equals(tokenInMap);
		} catch (Exception e) {
			log.error("Fiald to resolve Access-Token from reporitory, Data that was passed to Cotroller: Username: "
					+ user.getUsername() + ", Access-Token: " + token);
		}
		return false;
	}

	public void addAccessToken(String sendConformationMailTo, String token) {
		try {
			tokenRegister.put(sendConformationMailTo, token);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
