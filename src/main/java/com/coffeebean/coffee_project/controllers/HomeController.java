package com.coffeebean.coffee_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.coffeebean.coffee_project.entity.User;
import com.coffeebean.coffee_project.security.SecurityUtil;
import com.coffeebean.coffee_project.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;

	@GetMapping({"/", "/home"})
	public String home(Model model) {
		String email = SecurityUtil.getSessionUser();
		System.out.println(email);
		if(email != null) {
			User user = userService.findByEmail(email);
			model.addAttribute("user", user);
			// check if user is admin
			boolean isAdmin = userService.checkAdmin();
			model.addAttribute("isadmin", isAdmin);
		}
		return "home";
	}
}
