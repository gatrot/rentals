package com.rentals.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentals.entity.Advertisement;
import com.rentals.object.FilterDTO;
import com.rentals.service.manager.RentalsManager;

@RestController
public class AdvertisementController {

	@Autowired
	private RentalsManager rentalsManager;

	@PostMapping("public/advertisements")
	public ResponseEntity<Page<Advertisement>> searchAdsByCriteria(@RequestBody List<FilterDTO> filterDTOList,
			@RequestParam int page, @RequestParam int size) {
		return ResponseEntity.ok().body(rentalsManager.searchAdsByCriteria(filterDTOList, page, size));
	}

}
