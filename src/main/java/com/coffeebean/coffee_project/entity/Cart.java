package com.coffeebean.coffee_project.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(mappedBy="cart")
	private User user;
	
	@ManyToMany
	@JoinTable(name="carts_products",
			joinColumns= {@JoinColumn(name="cart_id", referencedColumnName="id")},
			inverseJoinColumns= {@JoinColumn(name="product_id", referencedColumnName="id")})
	private List<Product> products;
}
