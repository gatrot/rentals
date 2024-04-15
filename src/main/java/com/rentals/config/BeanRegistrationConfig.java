package com.rentals.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Configuration class will be called upon application upload to create and
 * configure some of the Spring-Beans
 * 
 * 
 * @author GatRot
 *
 */
@Configuration
public class BeanRegistrationConfig {

	/**
	 * Creates A {@link MessageSource} Spring-Bean that serving us as our
	 * 'application-message-repository'. configured based on the given Messages file
	 * - 'messages.properties'. this bean is to be used by the
	 * {@link MessageSourceAccessor} Spring-Bean which interrupted the messages
	 * based on the application user local OS language
	 * 
	 * @return {@link ReloadableResourceBundleMessageSource}
	 */
	@Bean
	MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	/**
	 * Creates A {@link MessageSourceAccessor} Spring-Bean by a given A
	 * {@link MessageSource} Spring-Bean in the constructor. This bean's purpose is
	 * to serve the application user store messages.
	 * 
	 * @return {@link MessageSourceAccessor}
	 */
	@Bean(name = "messageSourceAccessor")
	org.springframework.context.support.MessageSourceAccessor messageSourceAccessor() {
		return new MessageSourceAccessor(messageSource());
	}

	/**
	 * Creates A {@link LocalValidatorFactoryBean} Spring-Bean by a given A
	 * {@link MessageSource} Spring-Bean in the constructor. This bean's purpose is
	 * to serve as a Message-Validator
	 * 
	 * @return {@link MessageSourceAccessor}
	 */
	@Bean
	LocalValidatorFactoryBean getValidator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}
	
	@Bean
	ModelMapper modelMapper() {
	    return new ModelMapper();
	}

}