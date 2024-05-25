package com.rentals.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.rentals.service.OnApplicationStartupService;

//Declaring a Spring-Boot application Allow us to get Auto-Configuration and Auto-Component-Scanning and more for this java-module
@SpringBootApplication
//Declaring where the Spring-IOC-Container will be scanning for Spring-Beans
@ComponentScan("com.rentals")
//Declaring where the Spring-IOC-Container will be scanning for DB-Entities to be used by JPA/Hibernate ORM System
@EntityScan("com.rentals.entity")
//Declaring where the Spring-IOC-Container will be scanning for the DAO-Persistence layer to be used by JPA-Spring Data
@EnableJpaRepositories("com.rentals.repository")

public class RentalsApplication  {

	//Will init the Application Startup Services by {Link @PostConstruct }
	@Autowired
	OnApplicationStartupService startupService ;
	
	public static void main(String[] args) {
		SpringApplication.run(RentalsApplication.class, args);
	}



}
