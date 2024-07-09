package com.joaquincv.juegos.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.joaquincv.juegos.app.models.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

	@Query("SELECT o FROM purchases o WHERE o.user.id = :userId")
	List<Order> getOrderFromUser(@Param("userId") Long userId);

}
