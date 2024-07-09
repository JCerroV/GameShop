package com.joaquincv.juegos.app.services;

import java.util.List;

import com.joaquincv.juegos.app.bean.output.MessageDTO;
import com.joaquincv.juegos.app.bean.output.MessageNotificationDTO;
import com.joaquincv.juegos.app.models.entities.ChatMessage;

public interface ChatService {
	
	ChatMessage saveMessage(String id,MessageDTO message);
	
	List<MessageDTO>getMessages(Long id);
	
	void readMessages(String username,Long id);
	
	List<MessageNotificationDTO> getUnreadMessages(String username);
	
	
	
	

}
