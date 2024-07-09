package com.joaquincv.juegos.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.joaquincv.juegos.app.bean.input.GameRequest;
import com.joaquincv.juegos.app.bean.output.RateResponse;
import com.joaquincv.juegos.app.models.entities.Game;

public interface iGameService {
	
	List<Game> getAll(String filter);
	
	Page<Game> getAllGames(Pageable pageable,String filter);
	
	Page<Game> getGames(Pageable pageable,String filter);
	
	List<Game> filterByTitle(String filter);
	
	boolean findByTitle(String title);
	
	Game findById(Long id);
	
	void addGame(Game game);
	
	void updateGame(Long id, Game game);
	
	void deleteGame(Long id);
	
	Page<Game>getByCategories(List<Long>categoriesIds, Pageable pageable);

	
	//Valuate//
	
	RateResponse getMyRate(String username,Long id);
	
	void addNewRate(String username,Long id,int rate);
	
	void updateMyRate(Long id,int rate);
	
	void deleteMyRate(Long id);
}
