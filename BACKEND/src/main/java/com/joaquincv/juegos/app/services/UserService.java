package com.joaquincv.juegos.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.joaquincv.juegos.app.bean.input.PasswordRequest;
import com.joaquincv.juegos.app.bean.output.ResponseUser;
import com.joaquincv.juegos.app.bean.output.UserDTO;
import com.joaquincv.juegos.app.config.RegisterRequest;
import com.joaquincv.juegos.app.models.entities.User;

public interface UserService {
	
	void saveUser(RegisterRequest registerRequest);
	
	Page<User> listUsers(Pageable pageable,String filter);
	
	List<UserDTO>getUsers(String filter);
	
	User getUser(Long id);
	
	UserDTO getUserByUsername(String username,String currentUser);
	
	void deleteUser(Long id);
	
	boolean updateUser(ResponseUser user);
	
	void updateUserImage(String username,String imageUrl);
	
	User changeRole(String role,Long id);
	
	User changeStatus(Long id);
	
	boolean updatePassword(String username,PasswordRequest passwordRequest);
	
	boolean updatePrivacy(String username,String privacy);

	void updateBalance(String name, double deposit);

	boolean checkPassword(String name, String password);
}
