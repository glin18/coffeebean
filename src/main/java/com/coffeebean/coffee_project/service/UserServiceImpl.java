package com.coffeebean.coffee_project.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Override
	public String registerUser(UserModel userModel) {
		// We can't save a userModel directly in the database because it is a different entity
		// We will create a User and add the info from the userModel into the User
		User user = new User();
		user.setUsername(userModel.getUsername());
		user.setEmail(userModel.getEmail());
		user.setPassword(userModel.getPassword());
		Role role = roleRepository.findByName("USER");
		user.setRoles(Arrays.asList(role));
		userRepository.save(user);
		return "success";
	}

}
