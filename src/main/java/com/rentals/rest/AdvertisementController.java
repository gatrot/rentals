package com.rentals.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentals.entity.Advertisement;
import com.rentals.model.AdvertisementDTO;
import com.rentals.model.FilterDTO;
import com.rentals.model.WebResponse;
import com.rentals.service.manager.RentalsManager;

@RestController
public class AdvertisementController {

	@Autowired
	private RentalsManager rentalsManager;
	

	@PostMapping("public/advertisements/get-ads-by-criteria")
	public ResponseEntity<WebResponse> searchAdsByCriteria(@RequestBody List<FilterDTO> filterDTOList, @RequestParam int page, @RequestParam int size) {
		WebResponse response = rentalsManager.searchAdsByCriteria(filterDTOList, page, size) ;
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("private/advertisements/create")
	public ResponseEntity<WebResponse> createAd(@RequestBody AdvertisementDTO advertisementDTO, Authentication authentication) {
		WebResponse response = rentalsManager.createAd(advertisementDTO);
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping("private/advertisements/update")
	public ResponseEntity<WebResponse> updateAd(@RequestBody AdvertisementDTO advertisementDTO, Authentication authentication) {
		WebResponse response = rentalsManager.updateAd(advertisementDTO);
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping("private/advertisements/get-user-favorites")
	public ResponseEntity<WebResponse> getUserFavorites(Authentication authentication) {
		WebResponse response = rentalsManager.getUserFavorites();
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping("private/advertisements/get-user-ads")
	public ResponseEntity<WebResponse> getUserAds(Authentication authentication) {
		WebResponse response = rentalsManager.getUserAds();
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping("private/advertisements/add-user-favorites/{adId}")
	public ResponseEntity<WebResponse> getUserFavorites(@PathVariable("adId") String adId) {
		WebResponse response = rentalsManager.addUserFavorites(adId);
		return ResponseEntity.ok().body(response);
	}
}
