package com.joaquincv.juegos.app.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joaquincv.juegos.app.bean.input.CartRequest;
import com.joaquincv.juegos.app.models.entities.ShoppingCart;
import com.joaquincv.juegos.app.services.ShoppingCartService;

@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
public class ShoppingCartController {
	
	@Autowired
	private ShoppingCartService cartService;
	
	@PostMapping("/add")
	public void addToCart(Principal principal,@RequestBody Long gameId) {
		cartService.addToShoppingCart(principal.getName(),gameId);
		
	}
	
	@DeleteMapping("/remove/{id}")
	public void removeFromCart(@PathVariable Long id) {
		cartService.removeFromShoppingCart(id);
	}
	
	
	@GetMapping("")
	public ResponseEntity<List<ShoppingCart>> getShoppingCart(Principal principal) {
		List<ShoppingCart> cart= cartService.getCartFromUser(principal.getName());
		
		
		return new ResponseEntity<List<ShoppingCart>>(cart,HttpStatus.OK);
	}

}
