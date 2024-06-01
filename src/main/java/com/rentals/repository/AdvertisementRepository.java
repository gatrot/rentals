package com.rentals.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.rentals.entity.Advertisement;

@RepositoryRestResource(collectionResourceRel = "ads", path = "ads")
public interface AdvertisementRepository
		extends JpaRepository<Advertisement, UUID>, JpaSpecificationExecutor<Advertisement> {

	@RestResource(exported = false)
	@Query("SELECT ad FROM Advertisement AS ad WHERE ad.user.id = :userId")
	public List<Advertisement> getAdsCreatedByUser(@Param("userId") UUID userId);
	
	@RestResource(exported = false)
	@Query("SELECT ad FROM Advertisement AS ad WHERE ad.id = :id")
	public Advertisement getAdById(@Param("id") UUID id);

	@RestResource(exported = false)
	@Query("SELECT ad FROM Advertisement AS ad JOIN ad.favoritedBy fb WHERE fb.id = :userId")
	public List<Advertisement> getUserFavorites(@Param("userId") UUID userId);

	@RestResource(exported = false)
	@Query("SELECT ad FROM Advertisement AS ad WHERE ad.dateAdded < current_date - 30")
	public List<Advertisement> getAdsOverMonthOld();

	@RestResource(exported = false)
	@Query("SELECT ad FROM Advertisement AS ad WHERE ad.renewal < current_date - 7")
	public List<Advertisement> getAdsThatWerentRenewed();

	@Query("SELECT ad FROM Advertisement AS ad WHERE ad.id = :adId AND ad.user.id = :userId")
	public Advertisement getUserAdd(@Param("adId") UUID adId,  @Param("userId") UUID userId);
}
