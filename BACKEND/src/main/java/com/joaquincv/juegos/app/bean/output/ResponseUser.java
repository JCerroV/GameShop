package com.joaquincv.juegos.app.bean.output;

import java.util.ArrayList;
import java.util.List;

import com.joaquincv.juegos.app.models.entities.Game;
import com.joaquincv.juegos.app.models.entities.Profile;
import com.joaquincv.juegos.app.models.entities.Role;
import com.joaquincv.juegos.app.models.entities.ShoppingCart;
import com.joaquincv.juegos.app.models.entities.User;

public class ResponseUser {
	
	private String username;
	private String email;
	private double balance;
	private String status;
	private Role role;
	private Profile profile;
	private List<ShoppingCart>shoppingCart;
	private List<UserDTO>friends=new ArrayList<>();
	
	
	
	public ResponseUser(User user) {
		this.username=user.getUsername();
		this.email=user.getEmail();
		this.balance=user.getBalance();
		this.status=user.getStatus();
		this.role=user.getRole();
		this.profile=user.getProfile();
		this.shoppingCart=user.getShoppingCart();
	}
	
	public ResponseUser(String username, String email, int balance, String status, Role role, Profile profile) {
		super();
		this.username = username;
		this.email = email;
		this.balance = balance;
		this.status = status;
		this.role = role;
		this.profile = profile;
	}

	public ResponseUser() {
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public List<ShoppingCart> getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(List<ShoppingCart> shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public List<UserDTO> getFriends() {
		return friends;
	}

	public void setFriends(List<UserDTO> friends) {
		this.friends = friends;
	}

	
	

}
