package com.coffeebean.coffee_project.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {
	
	public static String getSessionUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// SecurityContextHolder is where Spring Security stores the details of who is authenticated
		// SecurityContext is obtained from the SecurityContext 
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			// the name of the principal is the email
			String email = authentication.getName();
			System.out.println(email);
			return email;
		}
		return null;
	}

}
