package com.coffeebean.coffee_project.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
	
	public static String getSessionUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// SecurityContextHolder is where Spring Security stores the details of who is authenticated
		// SecurityContext is obtained from the SecurityContextHolder, and contains the authentication
		// of the current authenticated user
		
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			String username = authentication.getName();
			return username;
		}
		return null;
	}

}
