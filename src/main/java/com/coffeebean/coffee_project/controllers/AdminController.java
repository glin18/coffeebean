package com.coffeebean.coffee_project.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.coffeebean.coffee_project.entity.Product;
import com.coffeebean.coffee_project.repository.ProductRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping
	public ModelAndView adminPage() {
		List<Product> productsList = productRepository.findAll();
		ModelAndView mav = new ModelAndView("admin");
		mav.addObject("products", productsList);
		return mav;
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
	
	@GetMapping("/products/delete")
	public void deleteProduct(@RequestParam Long productid, HttpServletResponse response ) throws IOException {
		Product deleteProduct = productRepository.findById(productid).get();
		productRepository.delete(deleteProduct);
		response.sendRedirect("/admin");
	}
	
	// Editing Product Form	
	@GetMapping("/products/edit")
	public ModelAndView editProductForm(@RequestParam Long productid) {
		Product editProduct = productRepository.findById(productid).get();
		ModelAndView mav = new ModelAndView("admin-edit-products");
		mav.addObject("editProduct", editProduct);
		return mav;
	}
	
	@PostMapping("/products/edit")
	public void editProduct(@ModelAttribute Product editedProduct, HttpServletResponse response) throws IOException {
		Product oldProduct = productRepository.findById(editedProduct.getId()).get();
		oldProduct.setDescription(editedProduct.getDescription());
		oldProduct.setName(editedProduct.getName());
		oldProduct.setPrice(editedProduct.getPrice());
		oldProduct.setStock(editedProduct.getStock());
		oldProduct.setImage(editedProduct.getImage());
		productRepository.save(oldProduct);
		response.sendRedirect("/admin");
	}
	
	
}
