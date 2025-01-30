package com.joaquincv.juegos.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.joaquincv.juegos.app.models.entities.GameComent;


@Repository
public interface GameComentRepository extends JpaRepository<GameComent,Long>{
	
	@Query("SELECT c From GameComent c WHERE c.game.id = :gameId ")
	 List<GameComent> getGameComents(@Param(value = "gameId") Long gameId);
	
}
