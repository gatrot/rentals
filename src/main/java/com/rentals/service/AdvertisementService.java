package com.rentals.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.rentals.entity.Advertisement;
import com.rentals.object.FilterDTO;

public interface AdvertisementService {

	Page<Advertisement> searchAdsByCriteria(List<FilterDTO> filterDTOList, int page, int size);

	Boolean createAd(Advertisement advertisement);

	Boolean updateAd(Advertisement advertisement);

	void deleteAd(Advertisement advertisement);
}
