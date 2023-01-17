package com.coffeebean.coffee_project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.coffeebean.coffee_project.entity.Cart;
import com.coffeebean.coffee_project.entity.Product;
import com.coffeebean.coffee_project.entity.User;
import com.coffeebean.coffee_project.repository.CartRepository;
import com.coffeebean.coffee_project.repository.ProductRepository;
import com.coffeebean.coffee_project.security.SecurityUtil;
import com.coffeebean.coffee_project.service.UserService;

@Controller
@RequestMapping("/products")
public class ShoppingController {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CartRepository cartRepository;

	@GetMapping("/beans")
	public ModelAndView showBeans() {
		ModelAndView mav = new ModelAndView("show-beans");
		String email = SecurityUtil.getSessionUser();
		User user = userService.findByEmail(email);
		List<Product> allProducts = productRepository.findAll();
		mav.addObject("allProducts", allProducts);
		mav.addObject("user", user);
		return mav;
	}
	
	@GetMapping("/cart")
	public String showCart(Model model) {
		String email = SecurityUtil.getSessionUser();
		User user = userService.findByEmail(email);
		model.addAttribute("user", user);
		return "cart";
	}
	
	@GetMapping("/addcart")
	public String addCart(@RequestParam Long productid) {
		String email = SecurityUtil.getSessionUser();
		Cart cart = userService.findByEmail(email).getCart();
		Product product = productRepository.findById(productid).get();
		List<Product> newProductList = cart.getProducts();
		newProductList.add(product);
		cart.setProducts(newProductList);
		cartRepository.save(cart);
		return "redirect:/products/cart?success";
	}
	
	@GetMapping("/cart/remove")
	public String removeCart(@RequestParam Long productid) {
		String email = SecurityUtil.getSessionUser();
		Cart cart = userService.findByEmail(email).getCart();
		Product product = productRepository.findById(productid).get();
		List<Product> newProductList = cart.getProducts();
		newProductList.remove(product);
		cart.setProducts(newProductList);
		cartRepository.save(cart);
		return "redirect:/products/cart?removed";
	}
}
