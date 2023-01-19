package com.coffeebean.coffee_project.service;

import com.coffeebean.coffee_project.entity.Review;

public interface ReviewService {
	
	public void save(Review review);
	
	public Review findById(Long reviewId);
	
	public void delete(Review review);
}
