package com.joaquincv.juegos.app.services;

import java.util.List;

import com.joaquincv.juegos.app.bean.output.RelationDTO;
import com.joaquincv.juegos.app.bean.output.UserDTO;
import com.joaquincv.juegos.app.models.entities.RelationShip;

public interface RelationShipService {
	
	List<UserDTO> getFriends(String username);
	
	RelationDTO getFriendRelation(String username,String friendname);
	
	List<UserDTO> getSendFriendRequest(String username);
	
	List<UserDTO> getReceivedFriendRequest(String username);
	
	RelationShip getRelation(String username,String friendname);
	
	void addNewFriend(String username, String friendUsername);
	
	void acceptFriend(String username, String friendUsername);
	
	void deleteRelation(String username, String friendUsername);
	

}
