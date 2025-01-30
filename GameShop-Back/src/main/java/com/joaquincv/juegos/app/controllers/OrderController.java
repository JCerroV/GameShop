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
import org.springframework.web.bind.annotation.RestController;

import com.joaquincv.juegos.app.models.entities.Game;
import com.joaquincv.juegos.app.models.entities.Order;
import com.joaquincv.juegos.app.services.OrderService;

@RestController
@CrossOrigin("*")
public class OrderController {
	
	@Autowired
	OrderService orderService;

	@GetMapping("/order/history")
	public ResponseEntity<List<Order>> getOrdersFromUser(Principal principal){
		try {
			return new ResponseEntity<List<Order>>(orderService.getOrderFromUser(principal.getName()),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Order>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@GetMapping("/admin/order/{username}/history")
	public ResponseEntity<List<Order>> getOrdersFromUserAdmin(@PathVariable String username){
		try {
			return new ResponseEntity<List<Order>>(orderService.getOrderFromUser(username),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Order>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@DeleteMapping("/admin/order/{id}/delete")
	public ResponseEntity<String>deleteOrder(@PathVariable Long id, Principal principal){
		try {
			orderService.deleteOrder(id,principal.getName());
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/order/new")
	public ResponseEntity<String> newOrder(Principal principal ,@RequestBody List<Game>games){
		try {
			orderService.addOrder(principal.getName(), games);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
