package com.coffeebean.coffee_project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	// our custom user details service
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	// password encoder so we don't store password in plain text
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		// we disable csrf for now. If we were to deploy this, we would enable it
		// we are permitting the paths listed in antMatchersfor any users
		// we are using form authentication with .formLogin()
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/admin/**")
			.hasAuthority("ADMIN")
			.antMatchers("/products/reviews/add")
			.hasAuthority("USER")
			.antMatchers("/users/signup", "/users/login", "/users/products", "/", "/home")
			.permitAll()
			.and()
			.formLogin()
				.loginPage("/users/login")
				.defaultSuccessUrl("/home?loggedin")
				.loginProcessingUrl("/users/login")
				.failureUrl("/users/login?error=true")
				.permitAll()
			.and()
			    .logout()
			    .logoutUrl("/users/logout")
			    .logoutSuccessUrl("/home?loggedout")
			    .invalidateHttpSession(true)
			    .deleteCookies("JSESSIONID");
		// When logging out we invalidate the session and we delete the cookie
		return http.build();
	}
	
	// Request -> Authentication Filter -> Authentication Manager -> Authentication Provider -> UserDetailsService -> DB
	// configure our authentication manager, we will not configure our own authentication provider
	public void configure(AuthenticationManagerBuilder builder) throws Exception {
		// pass in our userDetailsService and our passwordEncoder
		// Spring security will handle the rest for us
		builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	
}
