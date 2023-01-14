package com.coffeebean.coffee_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coffeebean.coffee_project.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
