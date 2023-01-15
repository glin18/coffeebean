package com.coffeebean.coffee_project.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.coffeebean.coffee_project.model.UserModel;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@GetMapping("/signup")
	public ModelAndView signUpForm() {
		ModelAndView mav = new ModelAndView("sign-up-form");
		UserModel userModel = new UserModel();
		mav.addObject("userModel", userModel);
		return mav;
	}
	
	@PostMapping("/signup")
	public void signUp(@ModelAttribute UserModel userModel, HttpServletResponse response) throws IOException {
		if(userModel.getPassword().contentEquals(userModel.getRepeatPassword())) {
			response.sendRedirect("/users/signup");
		}
	}
}
