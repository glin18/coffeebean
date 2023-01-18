package com.coffeebean.coffee_project.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coffeebean.coffee_project.entity.Role;
import com.coffeebean.coffee_project.entity.User;
import com.coffeebean.coffee_project.model.UserModel;
import com.coffeebean.coffee_project.repository.RoleRepository;
import com.coffeebean.coffee_project.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public String registerUser(UserModel userModel) {
		// We can't save a userModel directly in the database because it is a different entity
		// We will create a User and add the info from the userModel into the User
		User user = new User();
		user.setUsername(userModel.getUsername());
		user.setEmail(userModel.getEmail());
		// Use passwordEncoder.encode() to encode our password so it is not stored as plain text
		user.setPassword(passwordEncoder.encode(userModel.getPassword()));
		Role role = roleRepository.findByName("USER");
		// make sure that we setRoles as a list
		user.setRoles(Arrays.asList(role));
		userRepository.save(user);
		return "success";
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	// check if the user has the authority of ADMIN
	@Override
	public boolean checkAdmin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
		    return true;
		}
		return false;
	}

}
