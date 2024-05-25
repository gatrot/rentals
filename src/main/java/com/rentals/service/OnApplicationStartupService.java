package com.rentals.service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
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
import com.rentals.entity.User;
import com.rentals.object.AddressDTO;
import com.rentals.object.AdvertisementDTO;
import com.rentals.object.UserDetailsDTO;
import com.rentals.repository.AddressRepository;
import com.rentals.repository.AdvertisementRepository;
import com.rentals.repository.UserRepository;


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
	@Value("classpath:db/ads.json")
	Resource adsFile;
	@Value("classpath:db/users.json")
	Resource usersFile;
	@Value("classpath:db/addresses.json")
	Resource addressesFile;
	
	@PostConstruct
	public void startServices() {
		logger.info("Initializing On Application Startup Services");
		this.runMigration() ;
		this.startRenewalServices() ;
	}

	
	//Run DB migration
	private void runMigration() {
		logger.info("DB migration for the fallowing entities has started: User, Advertisement, Adderess");
		
		//Remove all Data
		userRepo.deleteAll();
		adRepo.deleteAll();
		addressRepo.deleteAll();
		
		List<User> users = null ;
		
		try {
			
			//Persist ads 
			try (InputStream inputStream = addressesFile.getInputStream()) {
				String addressContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
				List<AddressDTO> addressDtosList = mapper.readValue(addressContent, new TypeReference<List<AddressDTO>>() {});
				addressDtosList.forEach(addressDto -> {
					addressDto.setStreetNameHebrew(addressDto.getStreetNameHebrew().trim());
					addressDto.setStreetNameEnglish(addressDto.getStreetNameEnglish().trim());
				});
				inMemService.setInMemAddresses(addressDtosList);
			}
			
			//Persist ads 
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
			
			//Persist ads 
			try (InputStream inputStream = adsFile.getInputStream()) {
				String dsaContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
				List<AdvertisementDTO> adDtosList = mapper.readValue(dsaContent, new TypeReference<List<AdvertisementDTO>>() {});
				List<Advertisement> ads = new ArrayList<>() ;
				for (AdvertisementDTO adDto : adDtosList) {
					if (adDto!=null) {
						Advertisement ad = modelMapper.map(adDto, Advertisement.class);
						User randomeUser = users.get(new Random().nextInt(users.size()));
						List<AddressDTO> inMemAddresses = inMemService.getInMemAddresses() ;
						AddressDTO randomeAddress = inMemAddresses.get(new Random().nextInt(inMemAddresses.size()));
						Address address = modelMapper.map(randomeAddress, Address.class);
						address.setAd(ad);
						ad.setUser(randomeUser) ;
						ad.setAddress(address);
						ads.add(ad) ;
					}
				}
				adRepo.saveAll(ads);
			}

		} catch (Exception e) {
			logger.error("An error occurred while trying to persist entities to the DB", e);
			System.exit(-1);
		}
		
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
