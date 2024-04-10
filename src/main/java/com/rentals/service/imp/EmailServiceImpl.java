package com.rentals.service.imp;

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.rentals.object.EmailType;
import com.rentals.object.MailMessageRequest;
import com.rentals.service.UserService;
import com.rentals.util.RentalsUtil;


/**
 * This Service class implements Email Messaging services to be use by the 'Forgot my password flow' or 'Reset password flow'.
 * 
 * @author GatRot

 *
 */

@Component
@PropertySource("classpath:email.properties")
public class EmailServiceImpl {
	private static final Logger log = Logger.getLogger(EmailServiceImpl.class);

	/*
	 * Spring Dependency Injection
	 */
	@Autowired
	private UserService userService;
	@Autowired
	private AccessTokenServiceImp  accessTokenService ;
	
	
	/*
	 * Retrieving Environment Variables for email.properties
	 */
	@Value("${emailusername}")
	private String emailUsername;
	@Value("${emailPassword}")
	private String emailPassword;
	@Value("${replaceURL}")
	private String replaceURL;



/**
 * Method will send mail by a given {@link MailMessageRequest}  - (Configured for smtp.gmail.com)
 * @param messageRequest
 * @return {@link Boolean}
 */
	public boolean sendMail(MailMessageRequest messageRequest) {

		//Validate the user's email
		if (!RentalsUtil.emailValidator(messageRequest.getSentTo())) {
			return false;
		}

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.setProperty("mail.transport.protocol", "smtp");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailUsername,emailPassword);
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailUsername, "Rentals Application"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(messageRequest.getSentTo()));
			message.setSubject(messageRequest.getSubject());
			message.setText("");
			message.setContent(messageRequest.getBody(), "text/html");
			message.setReplyTo(messageRequest.getReplayTo());
			Transport.send(message);
			log.info("An e-mail has been sent to " + messageRequest.getSentTo() + " at --> " + new Date());
		} catch (Exception e) {
			log.info("Sending e-mail to ------> " + messageRequest.getSentTo() + " has failed.", e);
			return false;
		}
		return true;
	}




/**
 * Method will prepare the dedicated template to be send
 * @param sendConformationMailTo
 * @return {@link MailMessageRequest}
 */
	public MailMessageRequest prepareMail(String userEmail,HttpServletRequest req, EmailType emailType) {
		//Generate Access-Token
		String token = RentalsUtil.generateSaltString();
		//Create message body
		String body = createTemplate(req, token, userEmail ,emailType) ;
		String topic = emailType == EmailType.CONFIRMATION_EMAIL ? "Please confirm your email" : "Reset password" ;
		//Create Final message to be send
		MailMessageRequest message = new MailMessageRequest(userEmail,
				"Rental Application - " + topic ,body,"donotreply@gmail.com");
		//Register the <Access-Token/userEmail>
		accessTokenService.addAccessToken(token,userEmail);
		//Send Mail to user
		return message ;

	}
	
	/**
	 * Method will send a email by a given {@link MailMessageRequest} 
	 * @param messageRequest
	 * @return {@link Boolean}
	 */
	public boolean sendEmailTemplate(String sendConformationMailTo ,HttpServletRequest req, EmailType emailType) {
		MailMessageRequest message = prepareMail(sendConformationMailTo,req, emailType) ;
		if (message == null) {
			return false ;
		}
		return sendMail(message);
	}

	
	/**
	 * Method will create the email-template by a give {@link EmailType}
	 * @param req
	 * @param token
	 * @param sendConformationMailTo
	 * @return {@link String}
	 */
private String createTemplate(HttpServletRequest req, String token ,String sendConformationMailTo, EmailType emailType)   {
		String host = "http" + RentalsUtil.getMachineHostName(req);
		String urlToInject =null;
		String content = null;
		try {
			String linkType = 
					emailType == EmailType.CONFIRMATION_EMAIL ? "/rentals/public/confirmation-email"
					: "/rentals/reset_password.html";
			urlToInject = host + linkType + "?token=" + token + "&email=" + sendConformationMailTo;
			String htmlmailTemplate = 
					emailType == EmailType.CONFIRMATION_EMAIL ? "/email-confirmation-mail.html"
					: "/reset-password-mail.html";
			InputStream is = getClass().getResourceAsStream(htmlmailTemplate);
			content = RentalsUtil.getEmailTemplateFromClasspath(is);
		} catch (Exception e) {
			log.error("Faild to inject \"replaceURL\" in Email template", e);
		}
		content = content.replace(replaceURL, urlToInject);
		return content;
}


}