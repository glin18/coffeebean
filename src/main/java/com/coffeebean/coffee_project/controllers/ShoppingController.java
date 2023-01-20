package com.coffeebean.coffee_project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.coffeebean.coffee_project.entity.Cart;
import com.coffeebean.coffee_project.entity.Product;
import com.coffeebean.coffee_project.entity.Review;
import com.coffeebean.coffee_project.entity.User;
import com.coffeebean.coffee_project.model.ReviewModel;
import com.coffeebean.coffee_project.repository.CartRepository;
import com.coffeebean.coffee_project.repository.ProductRepository;
import com.coffeebean.coffee_project.security.SecurityUtil;
import com.coffeebean.coffee_project.service.ReviewService;
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
	
	@Autowired
	ReviewService reviewService;

	@GetMapping("/beans")
	public ModelAndView showBeans() {
		ModelAndView mav = new ModelAndView("show-beans");
		String email = SecurityUtil.getSessionUser();
		User user = userService.findByEmail(email);
		if( user != null) {
			boolean isAdmin = userService.checkAdmin();
			mav.addObject("isadmin", isAdmin);
		}
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
		
		//calculate total price
		double totalCost = 0;
		for(Product product: user.getCart().getProducts()) {
			totalCost += product.getPrice();
		}
		model.addAttribute("totalcost", totalCost);
		return "cart";
	}
	
	@GetMapping("/cart/add")
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
	
	@GetMapping("/cart/payment")
	public String paymentForm(Model model) {
		String email = SecurityUtil.getSessionUser();
		User user = userService.findByEmail(email);
		model.addAttribute("user", user);
		//calculate total price
		double totalCost = 0;
		for(Product product: user.getCart().getProducts()) {
			totalCost += product.getPrice();
		}
		model.addAttribute("totalcost", totalCost);
		return "payment";
	}
	
	@GetMapping("/reviews")
	public String showReviews(@RequestParam Long productid, Model model) {
		Product product = productRepository.findById(productid).get();
		List<Review> reviewList = product.getReviews();
		String currentUserEmail = SecurityUtil.getSessionUser();
		model.addAttribute("currentUserEmail", currentUserEmail);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("product", product);
		return "reviews";
	}
	
	@GetMapping("/reviews/add")
	public String addReviewsForm(@RequestParam Long productid, Model model) {
		Product product = productRepository.findById(productid).get();
		model.addAttribute("product", product);
		ReviewModel review = new ReviewModel();
		review.setProductid(productid);
		model.addAttribute("review", review);
		return "reviews-add";
	}
	
	@PostMapping("/reviews/add")
	public String addReview(@ModelAttribute ReviewModel reviewModel) {
		String email = SecurityUtil.getSessionUser();
		User user = userService.findByEmail(email);
		
		Long productid = reviewModel.getProductid();
		Review review = new Review();
		review.setUser(user);
		review.setComment(reviewModel.getComment());
		review.setRating(reviewModel.getRating());
		Product product = productRepository.findById(productid).get();
		review.setProduct(product);
		List<Review> reviewList = product.getReviews();
		reviewList.add(review);
		product.setReviews(reviewList);
//		productRepository.save(product);
		reviewService.save(review);
		return "redirect:/products/reviews?productid=" + productid + "&success";
	}
	
	@GetMapping("/reviews/delete")
	public String deleteReview(@RequestParam Long reviewid) {
		Review review = reviewService.findById(reviewid);
		Long productId = review.getProduct().getId();
		reviewService.delete(review);
		return "redirect:/products/reviews?productid="+ productId + "&delete"; 
	}
	
}
