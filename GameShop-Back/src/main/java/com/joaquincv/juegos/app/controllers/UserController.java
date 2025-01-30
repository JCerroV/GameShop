package com.joaquincv.juegos.app.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.joaquincv.juegos.app.bean.output.MessageNotificationDTO;
import com.joaquincv.juegos.app.bean.output.RelationDTO;
import com.joaquincv.juegos.app.bean.output.UserDTO;
import com.joaquincv.juegos.app.models.entities.RelationShip;
import com.joaquincv.juegos.app.models.entities.User;
import com.joaquincv.juegos.app.services.ChatService;
import com.joaquincv.juegos.app.services.ImageService;
import com.joaquincv.juegos.app.services.RelationShipService;
import com.joaquincv.juegos.app.services.UserService;


@RestController
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ImageService imageService;
	
	@Autowired
	ChatService chatService;
	
	@Autowired
	RelationShipService relationShipService;
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>>getUsers(@Param("filter")String filter){
		try {
			return new ResponseEntity<List<UserDTO>>(userService.getUsers(filter), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<UserDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/users/{username}")
	public ResponseEntity<UserDTO>getUser(Principal principal,@PathVariable String username){
		try {
			
			return new ResponseEntity<UserDTO>(userService.getUserByUsername(username,principal.getName()), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<UserDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("/profile/updateImage")
	public ResponseEntity<String>changeImage(@RequestParam(value = "image", required = false) MultipartFile image,Principal principal){
		try {
			String imageUrl = "http://localhost:8080/images/perfil.png";
	        if (image != null && !image.isEmpty()) {
	            imageUrl = imageService.saveImage(image,principal.getName());
	            }
	        userService.updateUserImage(principal.getName(),imageUrl);
			return new ResponseEntity<String>( HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/admin/users")
	public ResponseEntity<Page<User>>getUsers(
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10")int size,
			@RequestParam(defaultValue = "id")String order,
			@RequestParam(defaultValue = "true") boolean asc,
			@Param("filter")String filter)
	{
		
		Page<User> users=userService.listUsers(PageRequest.of(page, size,Sort.by(order)),filter);
		
		if (!asc) {
			users=userService.listUsers(PageRequest.of(page, size,Sort.by(order).descending()),filter);
		}
		return new ResponseEntity<Page<User>>(users,HttpStatus.OK);
	}
	
	@PutMapping("/admin/users/{id}/changeRole")
	public ResponseEntity<User> changeRole(@RequestBody String role,@PathVariable Long id){
		
		return new ResponseEntity<User>(userService.changeRole(role, id),HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/admin/users/{id}/changeStatus")
	public ResponseEntity<User> changeRole(@PathVariable Long id){
		
		return new ResponseEntity<User>(userService.changeStatus(id),HttpStatus.ACCEPTED);
		
	}
	
	
	/////////////////FRIENDSS///////////////////
	@PostMapping("/friends/add")
	public ResponseEntity<String>addFriend(@RequestBody String username,Principal principal){
		try {
			relationShipService.addNewFriend(principal.getName(), username);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/friends/{friendname}/delete")
	public ResponseEntity<String>deleteFriend(Principal principal,@PathVariable String friendname){
		try {
			relationShipService.deleteRelation(principal.getName(),friendname);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/friends/{friendname}/accept")
	public ResponseEntity<String>AcceptFriend(Principal principal,@PathVariable String friendname){
		try {
			relationShipService.acceptFriend(principal.getName(),friendname);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/friends")
	public ResponseEntity<List<UserDTO>>getFriends(Principal principal){
		try {
			
			return new ResponseEntity<List<UserDTO>>(relationShipService.getFriends(principal.getName()),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<UserDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/friend/{username}")
	public ResponseEntity<RelationDTO>getFriendRelation(Principal principal,@PathVariable String username){
		try {
			RelationDTO relationDTO=relationShipService.getFriendRelation(principal.getName(),username);
			relationDTO.setMessages(chatService.getMessages(relationDTO.getId())); 
			
			return new ResponseEntity<RelationDTO>(relationDTO,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RelationDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/read-messages")
	public void readMessages(@RequestBody Long id,Principal principal) {
		chatService.readMessages(principal.getName(),id);
	}
	
	@GetMapping("/unread-messages")
	public ResponseEntity<List<MessageNotificationDTO>> getUnreadMessagesCount(Principal principal) {
		try {
			return new ResponseEntity<List<MessageNotificationDTO>>(chatService.getUnreadMessages(principal.getName()),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<MessageNotificationDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@GetMapping("/friends/send-request")
	public ResponseEntity<List<UserDTO>>getSendRequest(Principal principal){
		try {
			
			return new ResponseEntity<List<UserDTO>>(relationShipService.getSendFriendRequest(principal.getName()),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<UserDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/friends/received-request")
	public ResponseEntity<List<UserDTO>>getReceivedRequest(Principal principal){
		try {
			
			return new ResponseEntity<List<UserDTO>>(relationShipService.getReceivedFriendRequest(principal.getName()),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<UserDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/friends/{username}/status")
	public ResponseEntity<String>getRelationStatus(Principal principal,@PathVariable String username){
		try {
			String relation="NO";
			RelationShip relationShip=relationShipService.getRelation(principal.getName(),username);
			if (relationShip != null) {
				if (relationShip.isAccept()) {
					relation="ACCEPT";
				}else {
					if (relationShip.getFriend().getUsername().equalsIgnoreCase(principal.getName())) {
						relation="PENDING1";
					}else {
						relation="PENDING2";
					}
					
				}
			}
			return new ResponseEntity<>(relation.toString(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	

}
