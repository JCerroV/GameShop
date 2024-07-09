package com.joaquincv.juegos.app.bean.output;

import java.util.Date;

import com.joaquincv.juegos.app.models.entities.GameComent;

public class CommentResponse {
	
	private Long id;
	private String username;
	private String text;
	private Date createdAt;
	private Date updatedAt;
	
	
	public CommentResponse(GameComent coment) {
		super();
		this.id = coment.getId();
		this.username = coment.getUser().getUsername();
		this.text = coment.getText();
		this.createdAt = coment.getCreatedAt();
		this.updatedAt = coment.getUpdatedAt();
	}
	
	public CommentResponse() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	
	
}
