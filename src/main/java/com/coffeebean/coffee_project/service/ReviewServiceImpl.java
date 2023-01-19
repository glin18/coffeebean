package com.coffeebean.coffee_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coffeebean.coffee_project.entity.Review;
import com.coffeebean.coffee_project.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;

	@Override
	public void save(Review review) {
		reviewRepository.save(review);
	}
	
	
}
