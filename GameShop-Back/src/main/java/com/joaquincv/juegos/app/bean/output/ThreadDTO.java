package com.joaquincv.juegos.app.bean.output;

import java.util.Date;
import java.util.List;

import com.joaquincv.juegos.app.models.entities.Thread;

public class ThreadDTO {

	private Long id;
	
	private String title;
	
	private Date createdAt;
	
	private UserDTO user;
	
	private Date updatedAt;
	
	private int visits;
	
	private int posts;
	
	private boolean status;
	
	

	public ThreadDTO() {
		super();
	}
	
	

	public ThreadDTO(Thread thread) {
		super();
		this.id = thread.getId();
		this.title = thread.getTitle();
		this.createdAt = thread.getCreatedAt();
		this.user=new UserDTO();
		this.user.setUsername(thread.getUser().getUsername());
		this.user.getProfile().setPhoto(thread.getUser().getProfile().getPhoto());;
		this.updatedAt = thread.getUpdatedAt();
		this.visits = thread.getVisit();
		this.status = thread.isStatus();
		this.posts=thread.getPosts();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getVisits() {
		return visits;
	}

	public void setVisits(int visits) {
		this.visits = visits;
	}

	public int getPosts() {
		return posts;
	}

	public void setPosts(int posts) {
		this.posts = posts;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	
	
	
	
}
