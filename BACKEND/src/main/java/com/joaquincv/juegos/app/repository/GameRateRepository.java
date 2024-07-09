package com.joaquincv.juegos.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.joaquincv.juegos.app.models.entities.GameRate;

@Repository
public interface GameRateRepository extends JpaRepository<GameRate, Long> {
	
	@Query("SELECT r FROM GameRate r WHERE r.game.id = :gameId")
	List<GameRate> getRatesFromGame(@Param("gameId")Long gameId);
	
	@Query("SELECT r FROM GameRate r WHERE r.game.id = :gameId AND r.user.id = :userId")
	Optional<GameRate> getMyRate(@Param("gameId")Long gameId, @Param("userId")Long userId );

}
