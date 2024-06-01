package com.rentals.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.rentals.entity.Advertisement;
import com.rentals.model.FilterDTO;

public interface AdvertisementService {

	Page<Advertisement> searchAdsByCriteria(List<FilterDTO> filterDTOList, int page, int size);

	Boolean createAd(Advertisement advertisement);

	Advertisement updateAd(Advertisement advertisement);

	void deleteAd(Advertisement advertisement);

	List<Advertisement> getUserFavorites(UUID userId);

	List<Advertisement> getUserAds(UUID userId);

	Advertisement getUserAdd(UUID adId, UUID userId);

	Advertisement getAdById(UUID adId);
}
