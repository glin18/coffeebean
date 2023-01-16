package com.coffeebean.coffee_project.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.coffeebean.coffee_project.entity.User;
import com.coffeebean.coffee_project.repository.UserRepository;

// UserDetailsService is basically what gets the users out of the database in the form of a UserDetails
// We need to create our own custom implementation of the UserDetailsService 
// and override the loadUserByUsername method

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Need to find FIRST by username because otherwise it might bring back more than one user
		User user = userRepository.findFirstByUsername(username);
		if(user != null) {
		// We need to return the User from the security.core.userdetails package
		// This is different to our User entity, which can be confusing
		// Perhaps it would have been better to call the User class by a different name such as UserEntity
		// We put the information from user into the security.core.userdetails.User object
			org.springframework.security.core.userdetails.User authUser = 
					new org.springframework.security.core.userdetails.User(
					user.getEmail(),
					user.getPassword(),
					// We use stream to put the roles into the SimpleGrantedAuthority
					// A fancy for loop
					// SimpleGrantedAuthority may allow us to have a more granular form of the role
					// basically allows us to have more specific roles
					// for example if we want a user to only be able to read certain places or not be able to create etc...
					user.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getName()))
						.collect(Collectors.toList())
					);
			return authUser;
		} else {
			// If user is null then we just throw an exception
			throw new UsernameNotFoundException("Invalid username or password");
		}
		
	}

}
