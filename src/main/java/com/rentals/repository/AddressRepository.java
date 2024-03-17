package com.rentals.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.rentals.entity.Address;

@RepositoryRestResource(collectionResourceRel = "Addresss", path = "Addresss")
public interface AddressRepository extends JpaRepository<Address, UUID>{
	
}
