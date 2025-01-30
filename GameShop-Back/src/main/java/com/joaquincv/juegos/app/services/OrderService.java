package com.joaquincv.juegos.app.services;

import java.util.List;

import com.joaquincv.juegos.app.models.entities.Game;
import com.joaquincv.juegos.app.models.entities.Order;

public interface OrderService {
	
	void addOrder(String username,List<Game>games);
	
	List<Order>getOrderFromUser(String username);
	
	void deleteOrder(Long id,String username);
	
	

}
