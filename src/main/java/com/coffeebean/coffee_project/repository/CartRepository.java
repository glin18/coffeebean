package com.coffeebean.coffee_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coffeebean.coffee_project.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
