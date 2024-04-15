package com.rentals.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rentals.entity.Address;
import com.rentals.entity.Advertisement;
import com.rentals.entity.Image;
import com.rentals.entity.User;
import com.rentals.object.AccommodationType;
import com.rentals.repository.AdvertisementRepository;
import com.rentals.repository.UserRepository;

//Declaring a Spring-Boot application Allow us to get Auto-Configuration and Auto-Component-Scanning and more for this java-module
@SpringBootApplication
//Declaring where the Spring-IOC-Container will be scanning for Spring-Beans
@ComponentScan("com.rentals")
//Declaring where the Spring-IOC-Container will be scanning for DB-Entities to be used by JPA/Hibernate ORM System
@EntityScan("com.rentals.entity")
//Declaring where the Spring-IOC-Container will be scanning for the DAO-Persistence layer to be used by JPA-Spring Data
@EnableJpaRepositories("com.rentals.repository")
public class RentalsApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AdvertisementRepository adRepo;

	public static void main(String[] args) {
		SpringApplication.run(RentalsApplication.class, args);
	}

	// TESTS
	@Override
	public void run(String... args) throws Exception {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		
		/**
		 * Creating new User + his Ad
		 */

		// Init user
		userRepo.deleteAll();
		adRepo.deleteAll();
		
		User userA = new User("orenh@gmail.com", "orenhoffman", "qweqwasdc1234");

		// Init AdsPublished
		List<Image> images = Arrays.asList(new Image("http://blublublu.com/image.jpg"));

		Advertisement adA = new Advertisement(new Date(), 2, "description", 23, 3, 3, AccommodationType.APARTMENT,
				new Date(), "yoffi", "0548458478", images, true, true, true, true, true, true, new Date());

		// Setting Address+Ad
		Address address = new Address("Somewhere", "City", "street Number", "22");
		address.setAd(adA);
		adA.setAddress(address);

		images.forEach(image -> image.setAd(adA));
		adA.setImages(images);
		String json = ow.writeValueAsString(adA);
		System.out.println(json);
		
		// Setting AdsPublished in User
		List<Advertisement> adsPublished = new ArrayList<>();
		adsPublished.add(adA);
		userA.setAdsPublished(adsPublished);
		userA.setAdsFavorited(adsPublished);

		// Setting User on Advertisement
		adA.setUser(userA);

		// Create User + Add
		userRepo.save(userA);

		
		/**
		 * Creating new User + And updating him with favorites
		 */
		
		//Create User
		User userB = new User("get@gmail.com", "gatrotman", "qweqwasdc1234");
		userRepo.save(userB);
		Optional<User> optionalUserB = userRepo.findById(userB.getId());
		User userBFromDB = optionalUserB.get();
		
		//Get Ad from DB
		UUID adAId = adA.getId();
		Advertisement adAFromDB = adRepo.findById(adAId).get();

		List<Advertisement> adsPublishedToUpdate = userB.getAdsFavorited() ;
		adsPublishedToUpdate.add(adAFromDB);
		userBFromDB.setAdsFavorited(adsPublishedToUpdate);

		userRepo.save(userB);

	}

}
