package com.joaquincv.juegos.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaquincv.juegos.app.bean.input.CartRequest;
import com.joaquincv.juegos.app.models.entities.Game;
import com.joaquincv.juegos.app.models.entities.ShoppingCart;
import com.joaquincv.juegos.app.models.entities.User;
import com.joaquincv.juegos.app.repository.GameRepository;
import com.joaquincv.juegos.app.repository.ShoppingCartRepository;
import com.joaquincv.juegos.app.repository.UserRepository;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
	
	@Autowired
    private ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	GameRepository gameRepository;

    public List<ShoppingCart> getCartFromUser(String username) {
    	try {
			User user=userRepository.findByUsername(username).orElse(null);
			if (user!=null) {
				return shoppingCartRepository.getCartFromUser(user.getId());
			}else {
				return new ArrayList<>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
    }

    public void addToShoppingCart(String username,Long gameId) {
    	
    	User user=userRepository.findByUsername(username).get();
    	Game game = gameRepository.findById(gameId).get();
    	ShoppingCart shoppingCart=new ShoppingCart(user,game);
        shoppingCartRepository.save(shoppingCart);
    }

    public void removeFromShoppingCart(Long shoppingCartId) {
        shoppingCartRepository.deleteById(shoppingCartId);
    }

}
