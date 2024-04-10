package com.rentals.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * A Web security configuration class for determining web behaviour,Authorities and user roles
 * @author Hoffman
 *
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	/*
	 *Spring Dependency Injection 
	 */
    @Autowired
    public UserDetailsService userDetailsService ;

    /*
     * Creates a {@link BCryptPasswordEncoder} Spring-Bean to be 
     * used when ever encryption  is need 
     * @return {@link BCryptPasswordEncoder}
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     *Main Web security configuration method 
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	  http
    	     .authorizeRequests()
    	     	 //Permit all HTTP requests that contain the word /global/ or trying to get a static resource (besides user_profile.html) 
    	         .antMatchers("/resources/**","/index.html","/public/**", "/").permitAll() 
    	         //Authenticate every HTTP request that trying to get to a authenticated-resorce by checking ROLE-USER
    	         .antMatchers("/user_profile.html" , "/private/**").authenticated()
    	         //Any request besides the above configuration : authenticate
    	         .anyRequest().authenticated()
    	         //Register a logout URL and a logout Success URL to be redirected to (upon successful user logout) 
    	         .and()
    	         .logout()
    	         .logoutUrl("/private/logout")
    	         .logoutSuccessUrl("/index.html") ;
    	        
    	         //Allow <iframe> to be open if the request came from the same origin
    	 http
    	 	.headers()
	         .frameOptions()
	         .sameOrigin()
	         .and()
             .csrf()
             .disable();
    	  

    }

    /*
     * Via injection we are configuring {@link AuthenticationManagerBuilder} 
     * to use {@link UserDetailsService} as the 
     * and {@link BCryptPasswordEncoder} as the password encryptor. 
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
    
    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }
    
    

    
}