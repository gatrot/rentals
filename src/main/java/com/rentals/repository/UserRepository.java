package com.rentals.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.rentals.entity.User;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends JpaRepository<User, UUID> {

	@RestResource(exported = false)
	@Query("SELECT user FROM User AS user WHERE user.email = :email")
	public User getUserByEmail(@Param("email") String email);

	@RestResource(exported = false)
	@Query("SELECT user FROM User AS user WHERE user.id = :userId")
	public User getUserById(@Param("userId") UUID userId);
}
