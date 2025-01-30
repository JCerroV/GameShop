package com.joaquincv.juegos.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaquincv.juegos.app.models.entities.Game;
import com.joaquincv.juegos.app.models.entities.Order;
import com.joaquincv.juegos.app.models.entities.Role;
import com.joaquincv.juegos.app.models.entities.ShoppingCart;
import com.joaquincv.juegos.app.models.entities.User;
import com.joaquincv.juegos.app.repository.GameRepository;
import com.joaquincv.juegos.app.repository.OrderRepository;
import com.joaquincv.juegos.app.repository.ShoppingCartRepository;
import com.joaquincv.juegos.app.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private ShoppingCartRepository cartRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	@Transactional
	@Override
	public void addOrder(String username, List<Game> games) {
		
		try {
			
			User user=userRepository.findByUsername(username).get();
			List<ShoppingCart> cart=cartRepository.getCartFromUser(user.getId());
			List<ShoppingCart> cart2=new ArrayList<>();
			cart2.addAll(cart);
			
			if (user.getBalance()-getTotal(games)<=0.00) {
				throw new Exception("Insufficient balance");
			}
			
			for (Game game : games) {
				Order order= new Order(user,game);
				orderRepository.save(order);
				for (ShoppingCart shoppingCart : cart2) {
					if (shoppingCart.getGame().getId()==game.getId()) {
						cartRepository.deleteById(shoppingCart.getId());
						cart.remove(shoppingCart);	
					}
				}
			}
			for (ShoppingCart shoppingCart : cart) {
				shoppingCart.setUser(user);
			}
			user.setShoppingCart(cart);
			user.setBalance(user.getBalance()-getTotal(games));
				userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	@Override
	public List<Order> getOrderFromUser(String username) {
		User user=userRepository.findByUsername(username).get();
		
		return orderRepository.getOrderFromUser(user.getId());
	}
	
	public double getTotal(List<Game>games) {
		double total=0;
		for (Game game : games) {
			total=total+game.getPrice();
		}
		return total;
		
	}

	@Override
	public void deleteOrder(Long id, String username) {
		User user=userRepository.findByUsername(username).orElse(null);
		if (user!=null && user.getRole()==Role.ADMIN) {
			Order order=orderRepository.findById(id).get();
			order.getUser().setBalance(order.getUser().getBalance()+order.getGame().getPrice());
			userRepository.save(order.getUser());
			orderRepository.delete(order);
		}
		
	}
	

}
