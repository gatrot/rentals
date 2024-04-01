package com.rentals.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rentals.entity.Advertisement;
import com.rentals.object.FilterDTO;
import com.rentals.repository.AdvertisementRepository;
import com.rentals.service.AdvertisementService;
import com.rentals.util.AdvertisementSpecificationUtil;

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

		return repo.findAll(AdvertisementSpecificationUtil.columnEqual(filterDTOList), pageable);
	}

}