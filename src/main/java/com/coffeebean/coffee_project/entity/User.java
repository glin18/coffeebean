package com.coffeebean.coffee_project.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.coffeebean.coffee_project.entity.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String email;
	private String username;
	private String password;
	
	private String address;
	private String tel;
	
	// Many to Many relationship between User and Roles
	// We create a join table
	// FetchType is EAGER because we always want the roles along with the User
	@ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	@JoinTable(
			name="users_roles",
			joinColumns= {@JoinColumn(name="user_id", referencedColumnName="id") },
			inverseJoinColumns= {@JoinColumn(name="role_id", referencedColumnName="id")}
			)
	private List<Role> roles = new ArrayList<>();
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="cart_id", referencedColumnName="id")
	private Cart cart = new Cart();
	
}
