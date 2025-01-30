package com.joaquincv.juegos.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.joaquincv.juegos.app.models.entities.Game;
import java.util.List;
import com.joaquincv.juegos.app.models.entities.Category;


@Repository
public interface GameRepository extends JpaRepository<Game, Long>{
	
	@Query("SELECT g FROM Game g WHERE g.title LIKE %:filter% AND g.deleted = false")
	Page<Game> findAll(Pageable pageable,@Param("filter")String filter);
	
	@Query("SELECT g FROM Game g WHERE g.title LIKE %:filter% AND g.deleted = false AND g.status=true")
	Page<Game> findAll1(Pageable pageable,@Param("filter")String filter);
	
	@Query("SELECT g FROM Game g JOIN g.categories c WHERE c.id IN :categoryIds AND g.deleted = false AND g.status = true GROUP BY g HAVING COUNT(c.id) = :categoryCount")
    Page<Game> findByCategory(Pageable pageable, @Param("categoryIds") List<Long> categoryIds, @Param("categoryCount") Long categoryCount);

	@Query("SELECT g FROM Game g WHERE g.deleted = false")
	Page<Game> findAll(Pageable pageable);
	
	@Query("SELECT g FROM Game g WHERE g.deleted = false AND g.status=true")
	Page<Game> findAll1(Pageable pageable);
	
	List<Game> findByTitle(String title);
	

}
