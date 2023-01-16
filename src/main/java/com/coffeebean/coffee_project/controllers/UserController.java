package com.coffeebean.coffee_project.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.coffeebean.coffee_project.entity.User;
import com.coffeebean.coffee_project.model.UserModel;
import com.coffeebean.coffee_project.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/signup")
	public ModelAndView signUpForm() {
		ModelAndView mav = new ModelAndView("sign-up-form");
		UserModel userModel = new UserModel();
		mav.addObject("userModel", userModel);
		return mav;
	}
	
	@PostMapping("/signup")
	public String signUp(@Valid @ModelAttribute UserModel userModel,  
			BindingResult result) {
		User existingUsername = userService.findByUsername(userModel.getUsername());
		User existingEmail = userService.findByEmail(userModel.getEmail());
		if(existingUsername != null || existingEmail!=null) {
			return "/users/signup";
		}
		
		if(!userModel.getPassword().contentEquals(userModel.getRepeatPassword()) || result.hasErrors()) {
			return "/users/signup";
		} 
		userService.registerUser(userModel);
		return "redirect:/home?success";
		
	}
}
