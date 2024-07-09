package com.joaquincv.juegos.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.joaquincv.juegos.app.models.entities.Game;
import com.joaquincv.juegos.app.models.entities.User;
import java.util.List;

 
public interface UserRepository extends JpaRepository<User,Long> {
	Optional<User> findByUsername(String username);
	
	@Query("SELECT u FROM User u WHERE u.username LIKE %:filter%")
	Page<User> findAll(Pageable pageable,@Param("filter")String filter);
	
	@Query("SELECT u FROM User u WHERE u.username LIKE %:filter%")
	List<User> findAll(@Param("filter")String filter);
	
	boolean existsByUsername(String username);
	
	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);
	
}
 