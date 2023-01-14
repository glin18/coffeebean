package com.coffeebean.coffee_project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.coffeebean.coffee_project.entity.Product;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@GetMapping
	public String adminPage() {
		return "admin";
	}
	
	@GetMapping("/products/add")
	public ModelAndView adminAddProducts() {
		ModelAndView mav = new ModelAndView("admin-add-products");
		Product newProduct = new Product();
		mav.addObject("newProduct", newProduct);
		return mav;
	}
}
