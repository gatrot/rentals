package com.rentals.service.imp;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rentals.entity.Advertisement;
import com.rentals.model.FilterDTO;
import com.rentals.repository.AdvertisementRepository;
import com.rentals.service.AdvertisementService;
import com.rentals.util.FilterCriteriaForAdsUtil;

@Service
public class AdvertisementServiceImp implements AdvertisementService {

	@Autowired
	private AdvertisementRepository repo;

	@Override
	public Page<Advertisement> searchAdsByCriteria(List<FilterDTO> filterDTOList, int page, int size) {

		if (page < 1)
			page = 1;

		if (size < 1)
			size = 10;

		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("dateAdded").descending());

		return repo.findAll(FilterCriteriaForAdsUtil.columnEqual(filterDTOList), pageable);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Boolean createAd(Advertisement advertisement) {
		Boolean res = false;
		try {
			advertisement = repo.save(advertisement);
			res = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	@Override
	@Transactional(readOnly = false)
	public Advertisement updateAd(Advertisement advertisement) {
		Boolean res = false;
		try {
			advertisement = repo.save(advertisement);
			return advertisement ;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null ;
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deleteAd(Advertisement advertisement) {
		repo.delete(advertisement);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Advertisement> getUserFavorites(UUID userId) {
		return repo.getUserFavorites(userId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Advertisement> getUserAds(UUID userId) {
		return repo.getAdsCreatedByUser(userId);
	}

	@Override
	public Advertisement getUserAdd(UUID adId, UUID userId) {
		return repo.getUserAdd(adId, userId);
	}
	
	@Override
	public Advertisement getAdById(UUID adId) {
		return repo.getAdById(adId); 
	}

}
