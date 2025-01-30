package com.joaquincv.juegos.app.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaquincv.juegos.app.bean.output.MessageDTO;
import com.joaquincv.juegos.app.bean.output.MessageNotificationDTO;
import com.joaquincv.juegos.app.models.entities.ChatMessage;
import com.joaquincv.juegos.app.models.entities.RelationShip;
import com.joaquincv.juegos.app.models.entities.User;
import com.joaquincv.juegos.app.repository.ChatRepository;
import com.joaquincv.juegos.app.repository.RelationShipRepository;
import com.joaquincv.juegos.app.repository.UserRepository;

@Service
public class ChatServiceImpl implements ChatService {
	
	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	RelationShipRepository relationShipRepository;

	@Override
	public ChatMessage saveMessage(String id,MessageDTO message) {
		
		RelationShip relationShip=relationShipRepository.findById(Long.valueOf(id)).orElse(null);
		User user=userRepository.findByUsername(message.getUsername()).orElse(null);
		if (relationShip!=null && user!=null) {
			ChatMessage chatMessage=new ChatMessage(message.getText(),user,relationShip);
			chatRepository.save(chatMessage);
		}
		
		
		return null;
		
	}

	@Override
	public List<MessageDTO> getMessages(Long id) {
		try {
			
			List<ChatMessage>messages=chatRepository.findByRelationShip(id);
			List<MessageDTO>messages1=new ArrayList<>();
			messages.forEach(message->messages1.add(new MessageDTO(message)) );
			return messages1;
			
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public void readMessages(String username, Long id) {
		try {
			List<ChatMessage>messages=chatRepository.findByRelationShip(id);
			List<ChatMessage>messages2=new ArrayList<>();
			User user=userRepository.findByUsername(username).orElse(null);
			if (!messages.isEmpty()&&user!=null) {
				for (ChatMessage message: messages) {
					if (message.getSender().getId()!=user.getId()) {
						message.setRead(true);
						messages2.add(message);	
					}				
				}
				chatRepository.saveAll(messages2);
			}
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public List<MessageNotificationDTO> getUnreadMessages(String username) {
		try {
			User user=userRepository.findByUsername(username).orElse(null);
			List<ChatMessage> unreadMessages=new ArrayList<>();
			List<MessageNotificationDTO> unreadMessagesCount=new ArrayList<>();
			unreadMessages=chatRepository.findUnreadMessagesByUsername(user.getId());
			for (ChatMessage unreadMessage : unreadMessages) {
				boolean finder=false;
				for (MessageNotificationDTO messageNotificationDTO : unreadMessagesCount) {
					if (unreadMessage.getSender().getUsername().equalsIgnoreCase(messageNotificationDTO.getUserDTO().getUsername())) {
						messageNotificationDTO.setUnreadMessageCount(messageNotificationDTO.getUnreadMessageCount()+1);
						finder=true;
					}
				}
				if (!finder) {
					unreadMessagesCount.add(new MessageNotificationDTO(unreadMessage.getSender()));
				}
			}
				
			
			return unreadMessagesCount;
		} catch (Exception e) {
			throw e;
		}
	}

}
