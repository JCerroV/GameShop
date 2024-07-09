package com.joaquincv.juegos.app.models.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ChatMessage {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;
    
    @ManyToOne
    @JoinColumn(name = "relation_id")
    private RelationShip relationShip;

    private Date createdAt;
    
    @Column(name = "is_read")
    private boolean read;
    
    
    public ChatMessage() {
    	
    }
	public ChatMessage(String text, User sender,RelationShip relationShip) {
		super();
		this.text = text;
		this.sender = sender;
		if (sender.getId()==relationShip.getUser().getId()) {
			this.receiver=relationShip.getFriend();
		}
		else {
			this.receiver=relationShip.getUser();
		}
		this.relationShip=relationShip;
		this.createdAt = new Date();
		this.read = false;
	}
	
	

	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
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

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}
	public RelationShip getRelationShip() {
		return relationShip;
	}
	public void setRelationShip(RelationShip relationShip) {
		this.relationShip = relationShip;
	}
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	
	
    
    

}
