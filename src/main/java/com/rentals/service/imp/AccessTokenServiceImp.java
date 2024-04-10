package com.rentals.service.imp;

import org.springframework.stereotype.Component;

import com.rentals.entity.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

@Component
public class AccessTokenServiceImp {
	// Access-Token registry
	Map<String, String> tokenRegistery;

	public AccessTokenServiceImp() {
		tokenRegistery = new ConcurrentHashMap<String, String>();
	}

	public void addAccessToken(String token, String sendConformationMailTo) {
		try {
			tokenRegistery.put(token, sendConformationMailTo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getUserEmailByToken(String token) {
		return tokenRegistery.get(token);
	}

}
