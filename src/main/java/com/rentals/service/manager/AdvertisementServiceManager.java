package com.rentals.service.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.rentals.entity.Advertisement;
import com.rentals.object.FilterDTO;
import com.rentals.service.AdvertisementService;

@Service
public class AdvertisementServiceManager {
	@Autowired
	private AdvertisementService advertisementService;

	public Page<Advertisement> searchAdsByCriteria(List<FilterDTO> filterDTOList, int page, int size) {
		return null;
	}
}
