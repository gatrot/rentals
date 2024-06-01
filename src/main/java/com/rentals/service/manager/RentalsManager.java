package com.rentals.service.manager;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.rentals.entity.Address;
import com.rentals.entity.Advertisement;
import com.rentals.entity.User;
import com.rentals.model.AdvertisementDTO;
import com.rentals.model.EmailType;
import com.rentals.model.FilterDTO;
import com.rentals.model.ResetPasswordRequest;
import com.rentals.model.UserDetailsDTO;
import com.rentals.model.WebResponse;
import com.rentals.service.AdvertisementService;
import com.rentals.service.SecurityService;
import com.rentals.service.UserService;
import com.rentals.service.imp.AccessTokenServiceImp;
import com.rentals.service.imp.EmailServiceImpl;
import com.rentals.service.imp.ValidatorServiceImp;

@Service
public class RentalsManager {

	@Autowired
	private ValidatorServiceImp regValidatorService;
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private AdvertisementService advertisementService;
	@Autowired
	private AccessTokenServiceImp accessTokenServiceImp;
	@Autowired
	private EmailServiceImpl emailServiceImp;
	@Autowired
	private ModelMapper modelMapper;

	public WebResponse searchAdsByCriteria(List<FilterDTO> filterDTOList, int page, int size) {
		Page<Advertisement> adsPages = advertisementService.searchAdsByCriteria(filterDTOList, page, size);
		List<Advertisement>  ads = adsPages.getContent();
		List<AdvertisementDTO>  adsDtos  = mapList(ads, AdvertisementDTO.class);
		return new WebResponse(true, HttpStatus.OK, adsDtos);
	}

	public WebResponse registration(UserDetailsDTO userDetailsDTO, BindingResult bindingResult,
			HttpServletRequest req) {
		regValidatorService.validate(userDetailsDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			String error = regValidatorService.getError(bindingResult);
			return new WebResponse(false, HttpStatus.BAD_REQUEST, error);
		}

		User user = new User(userDetailsDTO.getEmail(), userDetailsDTO.getUsername(), userDetailsDTO.getPassword());
		Boolean userCreated = userService.createUser(user);
		if (!userCreated) {
			return new WebResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create user");
		}

		if (!emailServiceImp.sendEmailTemplate(user.getEmail(), req, EmailType.CONFIRMATION_EMAIL)) {
			userService.deleteUser(user);
			return new WebResponse(false, HttpStatus.INTERNAL_SERVER_ERROR,"Failed to send confirmation email, rolling back transaction.");
		}

		userDetailsDTO.setId(user.getId());
		login(userDetailsDTO);
		userDetailsDTO.setPassword(null);
		return new WebResponse(true, HttpStatus.CREATED, Arrays.asList(userDetailsDTO));
	}

	public WebResponse login(UserDetailsDTO userDetailsDTO) {
		User user = securityService.login(userDetailsDTO.getEmail(), userDetailsDTO.getPassword());
		if (user != null) {
			userDetailsDTO.setId(user.getId());
			userDetailsDTO.setUsername(user.getUsername());
			userDetailsDTO.setPassword(null);
			return new WebResponse(true, HttpStatus.OK, Arrays.asList(userDetailsDTO));
		}
		return new WebResponse(false, HttpStatus.UNAUTHORIZED, "Username or password are incorrect.");
	}

	public WebResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
		return null;

	}

	public WebResponse confirmEmail(String token) {
		String email = accessTokenServiceImp.getUserEmailByToken(token);
		if (email != null) {
			User user = userService.findUserByEmail(email);
			if (user != null) {
				user.setEmailConfirmed(true);
				if (userService.updateUser(user)) {
					return new WebResponse(true, HttpStatus.PERMANENT_REDIRECT, "index.html");
				}
			}
		}
		return new WebResponse(false, HttpStatus.UNAUTHORIZED, "Incorrect token. Registry wasn't finished.");
	}

	public WebResponse createAd(AdvertisementDTO advertisementDTO) {
		try {
			Advertisement ad = mapOne(advertisementDTO, Advertisement.class);
			Address address = mapOne(advertisementDTO.getAddress(), Address.class);
			String userEmail = securityService.findLoggedInUserEmail();
			User user = userService.findUserByEmail(userEmail);

			ad.setAddress(address);
			address.setAd(ad);
			ad.getImages().forEach(image -> image.setAd(ad));
			ad.setUser(user);
			advertisementService.createAd(ad);

			List<Advertisement> adsPublished = user.getAdsPublished();
			adsPublished.add(ad);
			userService.updateUser(user);
			advertisementDTO.setId(ad.getId());
			return new WebResponse(true, HttpStatus.CREATED, Arrays.asList(advertisementDTO));

		} catch (Exception e) {
			return new WebResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create advertisement.");
		}
	}
	
	public WebResponse updateAd(AdvertisementDTO advertisementDTO) {
		String userEmail = securityService.findLoggedInUserEmail();
		User user = userService.findUserByEmail(userEmail);
		Advertisement ad = advertisementService.getUserAdd(advertisementDTO.getId(), user.getId()) ;
		if (ad == null) {
			return new WebResponse(false, HttpStatus.UNAUTHORIZED, "User trying to update someones else advertisement");
		}
		ad.setParking(advertisementDTO.getParking());
		ad.setFurnished(advertisementDTO.getFurnished());
		ad.setPets(advertisementDTO.getPets());
		ad.setDescription(advertisementDTO.getDescription());
		ad.setPrice(advertisementDTO.getPrice());
		ad = advertisementService.updateAd(ad) ;
		if(ad!=null) {
			advertisementDTO = mapOne(ad, AdvertisementDTO.class) ;
			return new WebResponse(true, HttpStatus.OK, Arrays.asList(advertisementDTO));
		}
		return new WebResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update Advertisement");
	}
	
	public WebResponse deleteAd(String adId) {
		String userEmail = securityService.findLoggedInUserEmail();
		User user = userService.findUserByEmail(userEmail);
		Advertisement ad = advertisementService.getUserAdd(UUID.fromString(adId), user.getId()) ;
		if (ad == null) {
			return new WebResponse(false, HttpStatus.UNAUTHORIZED, "User trying to delete someones else advertisement");
		}
		advertisementService.deleteAd(ad) ;
		return getUserAds();
	}
	
	public WebResponse getUserAds() {
		String userEmail = securityService.findLoggedInUserEmail();
		User user = userService.findUserByEmail(userEmail);
		List<Advertisement> userAds = advertisementService.getUserAds(user.getId()) ;
		List<AdvertisementDTO>  userAdsDtos  = mapList(userAds, AdvertisementDTO.class);
		return new WebResponse(true, HttpStatus.OK, userAdsDtos);
	}
	
	public WebResponse getUserFavorites() {
		String userEmail = securityService.findLoggedInUserEmail();
		User user = userService.findUserByEmail(userEmail);
		List<Advertisement> userFavAds = advertisementService.getUserFavorites(user.getId()) ;
		List<AdvertisementDTO>  userFavAdsDtos  = mapList(userFavAds, AdvertisementDTO.class);
		return new WebResponse(true, HttpStatus.OK, userFavAdsDtos);
	}
	
	public WebResponse addUserFavorites(String adId) {
		String userEmail = securityService.findLoggedInUserEmail();
		User user = userService.findUserByEmail(userEmail);
		List<Advertisement> userFavorited = user.getFavorites() ;
		userFavorited.add(advertisementService.getAdById(UUID.fromString(adId))) ;
		if(userService.updateUser(user)) {
			return this.getUserFavorites();
		}
		return new WebResponse(false, HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create user favorites.");
	}


	<S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
	    return source
	      .stream()
	      .map(element -> modelMapper.map(element, targetClass))
	      .collect(Collectors.toList());
	}
	
	<S, T> T mapOne(S source, Class<T> targetClass) {
	    return modelMapper.map(source, targetClass);
	}








}
