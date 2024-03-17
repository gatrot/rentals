package com.rentals.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Service;

import com.rentals.entity.Advertisement;
import com.rentals.object.AccommodationType;

@RepositoryRestResource(collectionResourceRel = "ads", path = "ads")
public interface AdvertisementRepository extends JpaRepository<Advertisement, UUID>{
	
	@RestResource(exported = false)
	@Query("SELECT ad FROM Advertisement AS ad WHERE ad.userId = :userId")
	public List<Advertisement> getAdsCreatedByUser(@Param("userId") UUID userId);
	
	@RestResource(exported = false)
	@Query("SELECT ad FROM Advertisement AS ad JOIN ad.favoritedBy fb WHERE fb.id = :userId")
	public List<Advertisement> getUserFavoriteAds(@Param("userId") UUID userId);
	
	@RestResource(exported = false)
	@Query("SELECT ad FROM Advertisement AS ad WHERE ad.dateAdded < current_date - 30")
	public List<Advertisement> getAdsOverMonthOld();
	
	@RestResource(exported = false)
	@Query("SELECT ad FROM Advertisement AS ad WHERE ad.renewal < current_date - 7")
	public List<Advertisement> getAdsThatWerentRenewed();
	
//	@Query("SELECT ad From Advertisement AS ad "
//			+ "WHERE (:city IS NULL OR ad.address.city = :city)"
//			+ "AND (:minRooms IS NULL OR ad.rooms >= :minRooms)"
//			+ "AND (:maxRooms IS NULL OR ad.maxRooms <= :maxRooms)"
//			+ "AND (:maxPrice IS NULL OR ad.price <= :maxPrice)"
//			+ "AND (:minFloor IS NULL OR ad.floor >= :minFloor)"
//			+ "AND (:maxFloor IS NULL OR ad.floor <= :maxFloor)"
//			+ "AND (:minSpace IS NULL OR ad.space >= :minSpace)"
//			+ "AND (:maxSpace IS NULL OR ad.space <= :maxSpace)"
//			+ "AND (:accType IS NULL OR ad.accType = :accType)"
//			+ "AND (:availability IS NULL OR ad.availability >= :availability)"
//			+ "AND (:garage IS NULL OR ad.garage = true)"
//			+ "AND (:parking IS NULL OR ad.parking = true)"
//			+ "AND (:elevator IS NULL OR ad.elevator = true)"
//			+ "AND (:balcony IS NULL OR ad.balcony = true)"
//			+ "AND (:furnished IS NULL OR ad.furnished = true)"
//			+ "AND (:pets IS NULL OR ad.pets = true)")
//	public List<Advertisement> searchAdsByCriteria(
//			@Param("city") String city, @Param("minRooms") int minRooms, @Param("maxRooms") int maxRooms,
//			@Param("maxPrice") int maxPrice, @Param("minFloor") int minFloor, @Param("maxFloor") int maxFloor,
//			@Param("minSpace") int minSpace, @Param("maxSpace") int maxSpace, @Param("accType") AccommodationType accType,
//			@Param("availability") Date availability, @Param("garage") Boolean garage, @Param("parking") Boolean parking,
//			@Param("elevator") Boolean elevator, @Param("balcony") Boolean balcony, @Param("furnished") Boolean furnished,
//			@Param("pets") Boolean pets);
}
