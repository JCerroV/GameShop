package com.joaquincv.juegos.app.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaquincv.juegos.app.models.entities.Game;
import com.joaquincv.juegos.app.models.entities.GameComent;
import com.joaquincv.juegos.app.models.entities.User;
import com.joaquincv.juegos.app.repository.GameComentRepository;
import com.joaquincv.juegos.app.repository.GameRepository;
import com.joaquincv.juegos.app.repository.UserRepository;

@Service
public class GameComentServiceImpl implements GameComentService {
	
	@Autowired
	GameComentRepository comentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	GameRepository gameRepository;
	
	@Override
	public List<GameComent> getAllCommentsFromGame(Long id) {
		return comentRepository.getGameComents(id);
	}

	@Override
	public GameComent getComment(Long id) {
		return comentRepository.findById(id).orElse(null);
	}

	@Override
	public void addNewComment(String username, Long gameId, String content) {
		Game game=gameRepository.findById(gameId).orElse(null);
		
		User user=userRepository.findByUsername(username).get();
		
		GameComent coment=new GameComent(content,game,user);
		
		comentRepository.save(coment);
		
	}

	@Override
	public void updateComment(Long id, String content) {
		GameComent coment= comentRepository.findById(id).orElse(null);
		coment.setText(content);
		coment.setUpdatedAt(new Date());
		comentRepository.save(coment);
		
	}

	@Override
	public void deleteComment(Long id) {
		comentRepository.deleteById(id);
		
	}

}
