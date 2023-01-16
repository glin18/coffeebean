package com.coffeebean.coffee_project.model;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO 

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
	
	@NotEmpty
	private String email;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String repeatPassword;
	
	@NotEmpty
	private String username;
}
