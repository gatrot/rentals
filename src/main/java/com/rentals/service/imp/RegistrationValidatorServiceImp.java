package com.rentals.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.rentals.entity.User;
import com.rentals.object.UserDetailsDTO;
import com.rentals.service.UserService;
import com.rentals.util.RentalsUtil;

/**
 * A Helper-Service class for validating user's Form-Param at login or
 * registration
 *
 */
@Service
public class RegistrationValidatorServiceImp implements Validator {

	/**
	 * Spring Dependency Injection
	 */
	@Autowired
	@Qualifier("messageSourceAccessor")
	private MessageSourceAccessor messageSourceAccessor;

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	/**
	 * Method will validate User's Form-Param and will inject errors (by a given
	 * preset of rules) into the {@link BindingResult} Spring-Bean
	 */
	@Override
	public void validate(Object o, Errors errors) {

		UserDetailsDTO details = (UserDetailsDTO) o;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "not.empty");

		if (!RentalsUtil.emailValidator(details.getUsername())) {
			errors.rejectValue("username", "email.not.valid");

		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "not.empty");

		if (!details.getUsername().isEmpty() && details.getUsername().length() < 3
				|| details.getUsername().length() > 30) {
			errors.rejectValue("username", "size.username");
		}

		if (userService.findUserByEmail(details.getEmail()) != null) {
			errors.rejectValue("email", "duplicate.user");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "not.empty");

		if (!details.getPassword().isEmpty() && details.getPassword().length() < 6
				|| details.getPassword().length() > 30) {
			errors.rejectValue("password", "size.password");
		}

	}

	/**
	 * Method will obtain all injected errors from the {@link BindingResult}
	 * Spring-Bean and will inject it into our {@link LoginError} POJO to be
	 * presented to the Front-End user.
	 * 
	 * @param bindingResult
	 * @return {@link LoginError}
	 */
	public String getError(BindingResult bindingResult) {
		String error = null;
		if (bindingResult.getAllErrors().isEmpty()) {
			ObjectError fieldError = bindingResult.getAllErrors().get(0);
			error = messageSourceAccessor.getMessage(fieldError);
		}
		return error;
	}

}