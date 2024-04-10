package com.rentals.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public abstract class RentalsUtil {

	public static final Logger logger = Logger.getLogger(RentalsUtil.class);
	
	public static boolean emailValidator(String email) {
		if (email != "" && email != null) {
			final Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = EMAIL_REGEX.matcher(email);
			return matcher.find();
		}

		return false;

	}
	
	public static String generateSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 18) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.toLowerCase().charAt(index));
		}

		return salt.toString();

	}
	
	public static String getMachineHostName(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		int idx = (((uri != null) && (uri.length() > 0)) ? url.indexOf(uri) : url.length());
		String host = url.substring(0, idx); //base url
		idx = host.indexOf("://");
		if(idx > 0) {
		  host = host.substring(idx); //remove scheme if present
		}
		
		return host ;
	}
	
	public static String getEmailTemplateFromClasspath(InputStream is) {
		String emailTemplate = null;
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			String line = buf.readLine();
			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line).append("\n");
				line = buf.readLine();
			}
			emailTemplate = sb.toString();
		} catch (Exception e) {
			logger.error("Failed to get Email-Template resource from classpath." ,e);
		}
		return emailTemplate ;
	}
}
