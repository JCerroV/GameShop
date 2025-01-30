package com.joaquincv.juegos.app.bean.output;

import java.util.Date;

import com.joaquincv.juegos.app.models.entities.ThreadPost;

public class PostDTO {
	
	private Long id;
	
    private String content;
    
    private Date createdAt;
    
    private UserDTO userDTO;
    
    private UserDTO editBy;
    
    private Date updatedAt;

	public PostDTO(ThreadPost post) {
		super();
		this.id = post.getId();
		this.content = post.getContent();
		this.createdAt = post.getCreatedAt();
		this.userDTO=new UserDTO();
		this.editBy=new UserDTO();
		
		this.userDTO.setUsername(post.getUser().getUsername());
		this.userDTO.getProfile().setPhoto(post.getUser().getProfile().getPhoto());
		this.updatedAt = post.getUpdatedAt();
		if (updatedAt != null) {
			this.editBy.setUsername(post.getModifyBy().getUsername());
			this.editBy.getProfile().setPhoto(post.getModifyBy().getProfile().getPhoto());
		}
		
		
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public UserDTO getEditBy() {
		return editBy;
	}

	public void setEditBy(UserDTO editBy) {
		this.editBy = editBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
    
}
