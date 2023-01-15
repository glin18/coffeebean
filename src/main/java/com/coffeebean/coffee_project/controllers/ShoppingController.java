package com.coffeebean.coffee_project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.coffeebean.coffee_project.entity.Product;
import com.coffeebean.coffee_project.repository.ProductRepository;

@Controller
@RequestMapping("/products")
public class ShoppingController {
	
	@Autowired
	ProductRepository productRepository;

	@GetMapping("/beans")
	public ModelAndView showBeans() {
		ModelAndView mav = new ModelAndView("show-beans");
		List<Product> allProducts = productRepository.findAll();
		mav.addObject("allProducts", allProducts);
		return mav;
	}
}
