package com.coffeebean.coffee_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coffeebean.coffee_project.entity.User;

public interface UserRepository extends JpaRepository<User, Long > {
	
	User findByEmail(String email);
	
	User findByUsername(String username);

	User findFirstByUsername(String username);
	
}
