package com.joaquincv.juegos.app.services;

import java.util.List;


import com.joaquincv.juegos.app.models.entities.GameComent;

public interface GameComentService  {

	List<GameComent> getAllCommentsFromGame(Long gameId);
	
	GameComent getComment(Long id);
	
	void addNewComment(String username,Long gameId,String content);
	
	void updateComment(Long id,String content);
	
	void deleteComment(Long id);
	
}
