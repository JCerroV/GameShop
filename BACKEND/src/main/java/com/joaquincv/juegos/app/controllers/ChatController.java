package com.joaquincv.juegos.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.joaquincv.juegos.app.bean.output.MessageDTO;
import com.joaquincv.juegos.app.services.ChatService;

@Controller
public class ChatController {
	
	@Autowired ChatService chatService;

	
	
	@MessageMapping("/chat/{roomId}")
	@SendTo("/topic/{roomId}")
	public List<MessageDTO> chat(@DestinationVariable String roomId, @Payload MessageDTO message) {
		
		chatService.saveMessage(roomId,message);
		
		return chatService.getMessages(Long.valueOf(roomId));
	}
	
}

