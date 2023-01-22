package com.coffeebean.coffee_project.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;

import com.coffeebean.coffee_project.entity.Product;
import com.coffeebean.coffee_project.entity.User;
import com.coffeebean.coffee_project.model.ChargeRequest;
import com.coffeebean.coffee_project.model.ChargeRequest.Currency;
import com.coffeebean.coffee_project.security.SecurityUtil;
import com.coffeebean.coffee_project.service.StripeService;
import com.coffeebean.coffee_project.service.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@Controller
public class ChargeController {

    @Autowired
    private StripeService stripeService;
    
    @Autowired
    private UserService userService;
    

    @PostMapping("/charge")
    public String charge(ChargeRequest chargeRequest, Model model)
      throws StripeException {
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(Currency.EUR);
        Charge charge = stripeService.charge(chargeRequest);
        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());
        
        String email = SecurityUtil.getSessionUser();
		User user = userService.findByEmail(email);
		model.addAttribute("user", user);
		List<Product> productList = user.getCart().getProducts();
		int totalCost = 0;
		for(Product product: user.getCart().getProducts()) {
			totalCost += product.getPrice();
		}
		model.addAttribute("amount", totalCost);
		List<Product> previousProductList = new ArrayList<>(productList);
		model.addAttribute("products", previousProductList);
		productList.removeAll(productList);
		user.getCart().setProducts(productList);
		userService.save(user);
        return "order-confirmation";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "order-confirmation";
    }
}
