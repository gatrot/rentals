package com.rentals.service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentals.entity.Address;
import com.rentals.entity.Advertisement;
import com.rentals.entity.Image;
import com.rentals.entity.User;
import com.rentals.model.AddressDTO;
import com.rentals.model.AdvertisementDTO;
import com.rentals.model.UserDetailsDTO;
import com.rentals.repository.AddressRepository;
import com.rentals.repository.AdvertisementRepository;
import com.rentals.repository.UserRepository;
import com.rentals.util.RentalsUtil;


@Service
public class OnApplicationStartupService {
	private static final Logger logger = LoggerFactory.getLogger(OnApplicationStartupService.class);
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AdvertisementRepository adRepo;
	@Autowired
	private AddressRepository addressRepo;
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private InMemCacheService inMemService;
	
	@Value("${run-migration}")
    private Boolean runMigration;
	
	@Value("classpath:db/ads.json")
	Resource adsFile;
	
	@Value("classpath:db/users.json")
	Resource usersFile;
	
	@Value("classpath:db/addresses.json")
	Resource addressesFile;
	
	@Value("classpath:db/badrooms.json")
	Resource badroomsFile;
	@Value("classpath:db/bathrooms.json")
	Resource bathroomsFile;
	@Value("classpath:db/kitchens.json")
	Resource kitchensFile;
	@Value("classpath:db/livingrooms.json")
	Resource livingroomsFile;
	
	Set<String> persistedImageUrls ;
	
	@PostConstruct
	public void startServices() {
		logger.info("Initializing On Application Startup Services");
		this.runMigration(runMigration) ;
		this.startRenewalServices() ;
	}

	
	//Run DB migration
	private void runMigration(Boolean runMigration) {
		if(!runMigration) {
			logger.info("runMigration env is set to off"); 
			return ;
		}
		
		logger.info("DB migration for the fallowing entities has started: User, Advertisement, Adderess, Image");
		
		//First Remove all Data
		userRepo.deleteAll();
		adRepo.deleteAll();
		addressRepo.deleteAll();
		
		persistedImageUrls = new HashSet<String>() ;
		List<User> users = null ;
		
		try {

			// Persist images
			List<String> badrooms = null;
			List<String> bathrooms = null;
			List<String> kitchens = null;
			List<String> livingrooms = null;
			InputStream badroomsInputStream = null;
			InputStream bathroomsInputStream = null;
			InputStream kitchensInputStream = null;
			InputStream livingroomsInputStream = null;
			try {
				badroomsInputStream = badroomsFile.getInputStream();
				bathroomsInputStream = bathroomsFile.getInputStream();
				kitchensInputStream = kitchensFile.getInputStream();
				livingroomsInputStream = livingroomsFile.getInputStream();

				String badroomsContent = new String(badroomsInputStream.readAllBytes(), StandardCharsets.UTF_8);
				String bathroomsContent = new String(bathroomsInputStream.readAllBytes(), StandardCharsets.UTF_8);
				String kitchensContent = new String(kitchensInputStream.readAllBytes(), StandardCharsets.UTF_8);
				String livingroomsContent = new String(livingroomsInputStream.readAllBytes(), StandardCharsets.UTF_8);

				badrooms = mapper.readValue(badroomsContent, new TypeReference<List<String>>() {});
				bathrooms = mapper.readValue(bathroomsContent, new TypeReference<List<String>>() {});
				kitchens = mapper.readValue(kitchensContent, new TypeReference<List<String>>() {});
				livingrooms = mapper.readValue(livingroomsContent, new TypeReference<List<String>>() {});

			} catch (Exception e) {
				logger.error("An error occurred while trying to persist images from DB file", e);
			} finally {
				badroomsInputStream.close();
				bathroomsInputStream.close();
				kitchensInputStream.close();
				livingroomsInputStream.close();
			}
			
			//Persist ads DB file
			try (InputStream inputStream = addressesFile.getInputStream()) {
				String addressContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
				List<AddressDTO> addressDtosList = mapper.readValue(addressContent, new TypeReference<List<AddressDTO>>() {});
				addressDtosList.forEach(addressDto -> RentalsUtil.trimStreetName(addressDto));
				inMemService.setInMemAddresses(addressDtosList);
			}
			
			//Persist ads DB file
			try (InputStream inputStream = usersFile.getInputStream()) {
				String userContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
				List<UserDetailsDTO> userDtosList = mapper.readValue(userContent, new TypeReference<List<UserDetailsDTO>>() {});
				users = 
						userDtosList
						.stream()
						.filter(userDto -> userDto != null)
						.map(userDto -> modelMapper.map(userDto, User.class) )
						.collect(Collectors.toList()) ;
				userRepo.saveAll(users);
			}
			
			//Persist ads DB file
			try (InputStream inputStream = adsFile.getInputStream()) {
				String dsaContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
				List<AdvertisementDTO> adDtosList = mapper.readValue(dsaContent, new TypeReference<List<AdvertisementDTO>>() {});
				List<Advertisement> ads = new ArrayList<>() ;
				for (AdvertisementDTO adDto : adDtosList) {
					this.createAdEntity(users, ads, adDto, badrooms, bathrooms, kitchens, livingrooms);
				}
				adRepo.saveAll(ads);
			}

		} catch (Exception e) {
			logger.error("An error occurred while trying to persist entities to the DB", e);
			System.exit(-1);
		}
		
	}


	private void createAdEntity(
			List<User> users, 
			List<Advertisement> ads, 
			AdvertisementDTO adDto,
			List<String> badrooms,
			List<String> bathrooms,
			List<String> kitchens,
			List<String> livingrooms) {
		if (adDto!=null) {
			Advertisement ad = modelMapper.map(adDto, Advertisement.class);
			User randomeUser = users.get(new Random().nextInt(users.size()));
			List<AddressDTO> inMemAddresses = inMemService.getInMemAddresses() ;
			AddressDTO randomeAddress = inMemAddresses.get(new Random().nextInt(inMemAddresses.size()));
			Address address = modelMapper.map(randomeAddress, Address.class);
			address.setAd(ad);
			ad.setUser(randomeUser) ;
			ad.setAddress(address);
			ad.setImages(this.createImagesForAd(badrooms, bathrooms, kitchens, livingrooms,ad));
			ads.add(ad) ;
		}
	}


	private List<Image> createImagesForAd(
			List<String> badrooms,
			List<String> bathrooms,
			List<String> kitchens,
			List<String> livingrooms,
			Advertisement ad) {
		List<Image> images = new ArrayList<Image>() ;
		
		images.add(new Image(this.getNextImage(badrooms), ad)) ;
		images.add(new Image(this.getNextImage(bathrooms), ad)) ;
		images.add(new Image(this.getNextImage(kitchens), ad)) ;
		images.add(new Image(this.getNextImage(livingrooms), ad)) ;
		
		images.add(new Image(this.getNextImage(badrooms), ad)) ;
		images.add(new Image(this.getNextImage(bathrooms), ad)) ;
		images.add(new Image(this.getNextImage(kitchens), ad)) ;
		images.add(new Image(this.getNextImage(livingrooms), ad)) ;
		
		return images;
	}

	//Defence mechanism from unique constraint violation on "image_pkey"
	private String getNextImage(List<String> imageList) {
		String randomeImage = imageList.get(0);
		while(persistedImageUrls.contains(randomeImage)) {
				logger.info("Current image was already assigned to a different Image entity, randomly picking a different one");
				randomeImage = imageList.get(new Random().nextInt(imageList.size()));
		}
		persistedImageUrls.add(randomeImage) ;
		return randomeImage ;
	}


	//Run Renewal Services nightly thread
	private void startRenewalServices() {
		// Calculate the time until 3 at night
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int delayInHour = hour < 3 ? 3 - hour  :  24 - (hour - 3);

		logger.info("Send Request Chargeback Email Thread operation has " + delayInHour + " hours till start");
		// Initialize new thread that will start at 6 in the morning in order to send
		// all unsent request chargeback messages
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				logger.info("Event Type Request scheduler has been initialized successfully");
				try {
					//eventsManager.sendEventTypeRequestsEmail() ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, delayInHour, 24, TimeUnit.HOURS);
	}

}
