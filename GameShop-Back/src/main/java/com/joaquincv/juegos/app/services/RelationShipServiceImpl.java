package com.joaquincv.juegos.app.services;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaquincv.juegos.app.bean.output.RelationDTO;
import com.joaquincv.juegos.app.bean.output.UserDTO;
import com.joaquincv.juegos.app.models.entities.RelationShip;
import com.joaquincv.juegos.app.models.entities.User;
import com.joaquincv.juegos.app.repository.RelationShipRepository;
import com.joaquincv.juegos.app.repository.UserRepository;

@Service
public class RelationShipServiceImpl implements RelationShipService{

	@Autowired
	RelationShipRepository relationShipRepository;
	
	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public List<UserDTO> getFriends(String username) {
		List<UserDTO> friends=new ArrayList<>();
		try {
			User user=userRepository.findByUsername(username).orElse(null);
			if (user !=null) {
				List<RelationShip>relationShips=relationShipRepository.getFriends(user.getId());
				relationShips.forEach(relation->friends.add(new UserDTO(relation.getFriend())));
				relationShips=relationShipRepository.getFriendsReverse(user.getId());
				relationShips.forEach(relation->friends.add(new UserDTO(relation.getUser())));
			}
			
			return friends;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public List<UserDTO> getSendFriendRequest(String username) {
		List<UserDTO> friends=new ArrayList<>();
		try {
			User user=userRepository.findByUsername(username).orElse(null);
			if (user !=null) {
				List<RelationShip>relationShips=relationShipRepository.getPendingFriends(user.getId());
				relationShips.forEach(relation->friends.add(new UserDTO(relation.getFriend())));
			}
			
			return friends;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<UserDTO> getReceivedFriendRequest(String username) {
		List<UserDTO> friends=new ArrayList<>();
		try {
			User user=userRepository.findByUsername(username).orElse(null);
			if (user !=null) {
				List<RelationShip>relationShips=relationShipRepository.getPendingFriendsReverse(user.getId());
				relationShips.forEach(relation->friends.add(new UserDTO(relation.getUser())));
			}
			
			return friends;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void addNewFriend(String username, String friendUsername) {
		try {
			User user=userRepository.findByUsername(username).get();
			User friend=userRepository.findByUsername(friendUsername).get();
			RelationShip relation=new RelationShip(user,friend);
			relationShipRepository.save(relation);
					
			} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void acceptFriend(String username, String friendUsername) {
		try {
			RelationShip relationShip=getRelation(friendUsername, username);
			if (relationShip != null) {
				relationShip.setAccept(true);
				relationShipRepository.save(relationShip);
			
			}
		} catch (Exception e) {
			throw e;
		}
		
		
	}

	@Override
	public void deleteRelation(String username, String friendUsername) {
		RelationShip relationShip=getRelation(friendUsername, username);
		relationShipRepository.deleteById(relationShip.getId());
		
	}

	@Override
	public RelationShip getRelation(String username, String friendname) {
		RelationShip relationShip= new RelationShip();
		User user=userRepository.findByUsername(username).orElse(null);
		User friend=userRepository.findByUsername(friendname).orElse(null);
		if (user !=null && friend != null) {
			relationShip=relationShipRepository.getRelation(friend.getId(),user.getId());
			if (relationShip == null) {
				relationShip=relationShipRepository.getRelation(user.getId(),friend.getId());
			}
		}
		
		return relationShip;
	}

	@Override
	public RelationDTO getFriendRelation(String username, String friendname) {
		try {
			RelationShip relationShip=new RelationShip();
			User user=userRepository.findByUsername(username).orElse(null);
			User friend=userRepository.findByUsername(friendname).orElse(null);
			if (user!=null && friend!=null) {
				relationShip= relationShipRepository.getRelation(friend.getId(),user.getId());
				if (relationShip==null) {
					relationShip=relationShipRepository.getRelation(user.getId(),friend.getId());
					}
				if (relationShip.getUser().getUsername().equalsIgnoreCase(friend.getUsername())) {
					relationShip.setUser(relationShip.getFriend());
					relationShip.setFriend(friend);
				}	
			}
			return new RelationDTO(relationShip);
		} catch (Exception e) {
			throw e;
		}
	}
}
