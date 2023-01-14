package com.coffeebean.coffee_project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ShoppingController {
	
	@GetMapping("/beans")
	public String showBeans() {
		return "show-beans";
	}
}
