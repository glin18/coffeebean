package com.coffeebean.coffee_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO 

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
	private String email;
	private String password;
	private String repeatPassword;
	private String username;
}
