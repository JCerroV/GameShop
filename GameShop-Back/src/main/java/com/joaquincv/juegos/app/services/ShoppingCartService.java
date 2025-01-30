package com.joaquincv.juegos.app.services;

import java.util.List;

import com.joaquincv.juegos.app.bean.input.CartRequest;
import com.joaquincv.juegos.app.models.entities.ShoppingCart;

public interface ShoppingCartService {
	
	void addToShoppingCart(String username,Long gameId);
	
	void removeFromShoppingCart(Long id);
	
	List<ShoppingCart>getCartFromUser(String username);

}
