package com.coffeebean.coffee_project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		// we disable csrf for now. If we were to deploy this, we would enable it
		// we are permitting the paths listed in antMatchersfor any users
		// we are using form authentication with .formLogin()
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/users/signup", "/users/login", "/users/products", "/", "/home")
			.permitAll()
			.and()
			.formLogin(form -> form
				.loginPage("/users/login")
				.defaultSuccessUrl("/home?loggedin")
				.loginProcessingUrl("/users/login")
				.failureUrl("/users/login?error=true")
				.permitAll()
			).logout(logout -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/users/logout"))
				.permitAll()
					);
		return http.build();
	}
	
}
