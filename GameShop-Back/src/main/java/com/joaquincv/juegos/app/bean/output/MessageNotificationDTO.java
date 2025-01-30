package com.joaquincv.juegos.app.bean.output;

import com.joaquincv.juegos.app.models.entities.User;

public class MessageNotificationDTO {
	
	private UserDTO userDTO;
	
	private int unreadMessageCount;
	
	public MessageNotificationDTO() {
	}

	public MessageNotificationDTO(User user) {
		super();
		this.userDTO = new UserDTO(user);
		this.unreadMessageCount = 1;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public int getUnreadMessageCount() {
		return unreadMessageCount;
	}

	public void setUnreadMessageCount(int unreadMessageCount) {
		this.unreadMessageCount = unreadMessageCount;
	}
	
	

}
