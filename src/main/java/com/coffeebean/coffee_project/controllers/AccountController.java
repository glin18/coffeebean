package com.coffeebean.coffee_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coffeebean.coffee_project.entity.User;
import com.coffeebean.coffee_project.repository.UserRepository;
import com.coffeebean.coffee_project.security.SecurityUtil;
import com.coffeebean.coffee_project.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping()
	public String getAccountInfo(Model model) {
		String email = SecurityUtil.getSessionUser();
		User user = userService.findByEmail(email);
		if(user != null) {
			model.addAttribute("user", user);
			return "account";
		} else {
			return "redirect:/users/login?loginrequired";
		}
	}
	
	@GetMapping("/edit")
	public String editAccountInfoForm(Model model) {
		String email = SecurityUtil.getSessionUser();
		User user = userService.findByEmail(email);
		if(user != null) {
			model.addAttribute("user", user);
			return "account-edit";
		} else {
			return "redirect:/users/login?loginrequired";
		}
	}
	
	@PostMapping("/edit")
	public String editAccount(@ModelAttribute User user) {
		User UserInfo = userService.findById(user.getId());
		UserInfo.setAddress(user.getAddress());
		UserInfo.setTel(user.getTel());
		userRepository.save(UserInfo);
		return "redirect:/account?success";
	}
}
