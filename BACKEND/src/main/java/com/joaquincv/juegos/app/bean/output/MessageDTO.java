package com.joaquincv.juegos.app.bean.output;

import com.joaquincv.juegos.app.models.entities.ChatMessage;

public class MessageDTO {

	private Long id;
	
	private String text;
	
	private String username;
	
	
	
	private String sendAt;
	
	public MessageDTO() {
		
	}

	public MessageDTO(ChatMessage chatMessage) {
		super();
		this.id = chatMessage.getId();
		this.text = chatMessage.getText();
		this.username = chatMessage.getSender().getUsername();
		this.sendAt = chatMessage.getCreatedAt().toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSendAt() {
		return sendAt;
	}

	public void setSendAt(String sendAt) {
		this.sendAt = sendAt;
	}

	
	
	
	
	
}
