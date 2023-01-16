package com.coffeebean.coffee_project.service;

import com.coffeebean.coffee_project.entity.User;
import com.coffeebean.coffee_project.model.UserModel;

public interface UserService {
	public String registerUser(UserModel userModel);
	
	public User findByEmail(String email);
	
	public User findByUsername(String username);
}
