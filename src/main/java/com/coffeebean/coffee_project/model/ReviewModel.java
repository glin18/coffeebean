package com.coffeebean.coffee_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewModel {
	private int rating;
	private String comment;
	private Long productid;
}
