package com.rentals.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rentals.object.AddressDTO;

@Service
public class InMemCacheService {
	
	List<AddressDTO> inMemAddresses ;
	
	public InMemCacheService() {
		
	}
	
	public void setInMemAddresses(List<AddressDTO> addressesDtos) {
		if (inMemAddresses == null || inMemAddresses.isEmpty()) {
			inMemAddresses = addressesDtos ;
		}
	}
	
	public List<AddressDTO> getInMemAddresses() {
		return this.inMemAddresses ;
	}
	
	
	
	

}
