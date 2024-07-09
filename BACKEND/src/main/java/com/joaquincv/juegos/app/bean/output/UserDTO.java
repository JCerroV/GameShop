package com.joaquincv.juegos.app.bean.output;

import java.util.ArrayList;
import java.util.List;

import com.joaquincv.juegos.app.models.entities.Game;
import com.joaquincv.juegos.app.models.entities.Profile;
import com.joaquincv.juegos.app.models.entities.User;

public class UserDTO {
	
	private String username;
	
	private Profile profile;
	
	private List<UserDTO> friends;
	
	private List<Game> library;
	
	public UserDTO() {
		this.profile=new Profile();
	}

	public UserDTO(User user) {
		super();
		this.username = user.getUsername();
		this.profile= new Profile();
		this.friends=new ArrayList<>();
		this.library=new ArrayList<>();
		this.profile.setPhoto(user.getProfile().getPhoto());
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public List<UserDTO> getFriends() {
		return friends;
	}

	public void setFriends(List<UserDTO> friends) {
		this.friends = friends;
	}

	public List<Game> getLibrary() {
		return library;
	}

	public void setLibrary(List<Game> library) {
		this.library = library;
	}

	

	
	
	
	
	

}
