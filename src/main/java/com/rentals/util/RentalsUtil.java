package com.rentals.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RentalsUtil {

	public static boolean emailValidator(String email) {
		if (email != "" && email != null) {
			final Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = EMAIL_REGEX.matcher(email);
			return matcher.find();
		} else {
			//logger.error("Field email have a null value in it.", new RuntimeException("At least one attributes returned a null value."));
			return false;
		}

	}
}
