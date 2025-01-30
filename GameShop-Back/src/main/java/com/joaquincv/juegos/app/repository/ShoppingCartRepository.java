package com.joaquincv.juegos.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.joaquincv.juegos.app.models.entities.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
	
	@Query("SELECT c FROM ShoppingCart c WHERE c.user.id = :userId")
	List<ShoppingCart> getCartFromUser(@Param("userId") Long userId);

}
