package com.joaquincv.juegos.app.bean.output;

import java.util.ArrayList;
import java.util.List;

import com.joaquincv.juegos.app.models.entities.RelationShip;
import com.joaquincv.juegos.app.models.entities.User;

public class RelationDTO {
	
	private Long id;
	
	private UserDTO user;
	
	private UserDTO friend;
	
	private List<MessageDTO>messages=new ArrayList<>();
	
	
	public RelationDTO() {
	}

	public RelationDTO(RelationShip relationShip) {
		super();
		this.id = relationShip.getId();
		this.user=new UserDTO(relationShip.getUser());
		this.friend = new UserDTO(relationShip.getFriend());
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDTO getFriend() {
		return friend;
	}

	public void setFriend(UserDTO friend) {
		this.friend = friend;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public List<MessageDTO> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageDTO> messages) {
		this.messages = messages;
	}
	
	
	
	
}
