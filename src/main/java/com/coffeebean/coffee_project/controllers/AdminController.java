package com.coffeebean.coffee_project.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.coffeebean.coffee_project.entity.Product;
import com.coffeebean.coffee_project.repository.ProductRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping
	public String adminPage() {
		return "admin";
	}
	
	@GetMapping("/products/add")
	public ModelAndView addProductForm() {
		ModelAndView mav = new ModelAndView("admin-add-products");
		Product newProduct = new Product();
		mav.addObject("newProduct", newProduct);
		return mav;
	}
	
	@PostMapping("/products/add")
	public void addProduct(@ModelAttribute Product newProduct, HttpServletResponse response) throws IOException {
		productRepository.save(newProduct);
		response.sendRedirect("/admin");
	}
	
	
}
