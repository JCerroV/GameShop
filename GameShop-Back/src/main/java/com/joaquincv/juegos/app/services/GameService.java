package com.joaquincv.juegos.app.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.joaquincv.juegos.app.bean.output.RateResponse;
import com.joaquincv.juegos.app.models.entities.Game;
import com.joaquincv.juegos.app.models.entities.GameRate;
import com.joaquincv.juegos.app.models.entities.User;
import com.joaquincv.juegos.app.repository.CategoryRepository;
import com.joaquincv.juegos.app.repository.GameRateRepository;
import com.joaquincv.juegos.app.repository.GameRepository;
import com.joaquincv.juegos.app.repository.UserRepository;

@Service
public class GameService implements iGameService{
	
	@Autowired
	GameRepository gameRepository;
	
	@Autowired
	GameRateRepository gameRateRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public List<Game> getAll(String filter) {		
		return gameRepository.findAll();
	}

	@Override
	public List<Game> filterByTitle(String filter) {
		return null;
	}

	@Override
	public Game findById(Long id) {
		return gameRepository.findById(id).get();
	}
	@Override
	public void addGame(Game game) {	
		gameRepository.save(game);	
	}

	@Override
	public void updateGame(Long id,Game game) {	
		Game gameUpdate=gameRepository.findById(id).get();
		gameUpdate.setTitle(game.getTitle());
		gameUpdate.setAuthor(game.getAuthor());
		gameUpdate.setDescription(game.getDescription());
		gameUpdate.setImage(game.getImage());
		gameUpdate.setPrice(game.getPrice());
		gameUpdate.setCategories(game.getCategories());
		gameUpdate.setStatus(game.isStatus());
		gameUpdate.setUpdatedAt(new Date());		
		gameRepository.save(gameUpdate);
		
	}

	@Override
	public void deleteGame(Long id) {
		Game game=gameRepository.findById(id).get();
		game.setDeleted(true);
		gameRepository.save(game);
	}

	@Override
	public Page<Game> getAllGames(Pageable pageable,String filter) {
		if (filter!=""&& filter!=null) {
			if (filter.startsWith("#")) {
				//return gameRepository.findById(Long.parseLong(filter.substring(1))).get();
			}else {
				return gameRepository.findAll(pageable ,filter);
			}
			
		}
		return gameRepository.findAll(pageable);
		
	}
	
	@Override
	public Page<Game> getGames(Pageable pageable,String filter) {
		if (filter!=""&& filter!=null) {
			if (filter.startsWith("#")) {
				//return gameRepository.findById(Long.parseLong(filter.substring(1))).get();
			}else {
				return gameRepository.findAll1(pageable ,filter);
			}
			
		}
		return gameRepository.findAll1(pageable);
		
	}

	@Override
	public boolean findByTitle(String title) {
		if (gameRepository.findByTitle(title).isEmpty()) {
			return false;
			
		}
		return true;
	}

	@Override
	public RateResponse getMyRate(String username, Long id) {
		
		User user=userRepository.findByUsername(username).orElse(null);
		GameRate gameRate=gameRateRepository.getMyRate(id, user.getId()).orElse(new GameRate());
		RateResponse rateResponse=new RateResponse(gameRate);
		
		return rateResponse;
	}

	@Override
	public void addNewRate(String username, Long id, int rate) {
		try {
			User user=userRepository.findByUsername(username).orElse(null);
			Game game = gameRepository.findById(id).orElse(null);
			
			GameRate gameRate=new GameRate(rate,game,user);
			gameRateRepository.save(gameRate);
			
			calcAverage(game);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	@Override
	public void updateMyRate(Long id, int rate) {
		GameRate gameRate=gameRateRepository.findById(id).orElse(null);
		gameRate.setRate(rate);
		gameRateRepository.save(gameRate);
		
	}

	@Override
	public void deleteMyRate(Long id) {
		GameRate rate=gameRateRepository.findById(id).get();
		gameRateRepository.deleteById(id);
		calcAverage(rate.getGame());
		
	}

	@Override
	public Page<Game> getByCategories(List<Long> categoriesIds, Pageable pageable) {
		try {
			long countIds=categoriesIds.size();
			
			Page<Game>games=gameRepository.findByCategory(pageable, categoriesIds, countIds);
			return games;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	private void calcAverage(Game game) {
		
		game.setValuate(0);
		List<GameRate>rates=gameRateRepository.getRatesFromGame(game.getId());
		if (rates.size()>0) {
			rates.forEach(r-> game.setValuate(game.getValuate()+r.getRate()));
			game.setValuate(game.getValuate()/rates.size());
		}
		gameRepository.save(game);
		
	}
	

}
