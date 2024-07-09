package com.joaquincv.juegos.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.joaquincv.juegos.app.bean.input.PasswordRequest;
import com.joaquincv.juegos.app.bean.output.ResponseUser;
import com.joaquincv.juegos.app.bean.output.UserDTO;
import com.joaquincv.juegos.app.config.RegisterRequest;
import com.joaquincv.juegos.app.models.entities.Order;
import com.joaquincv.juegos.app.models.entities.Privacy;
import com.joaquincv.juegos.app.models.entities.Profile;
import com.joaquincv.juegos.app.models.entities.Role;
import com.joaquincv.juegos.app.models.entities.User;
import com.joaquincv.juegos.app.repository.ProfileRepository;
import com.joaquincv.juegos.app.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RelationShipService relationShipService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	ProfileRepository profileRepository;
	
	@Autowired
	AuthService authService;
	
	@Autowired
	private PasswordEncoder encoder;

	@Transactional
	@Override
	public void saveUser(RegisterRequest registerRequest) {
		
		User user=new User(registerRequest.getUsername(),encoder.encode(registerRequest.getPassword()),registerRequest.getEmail());
		Profile profile=new Profile(registerRequest.getName(),registerRequest.getLastname(),registerRequest.getPhone(),registerRequest.getCountry());
		user.setProfile(profile);
		profile.setUser(user);
		user=userRepository.save(user);
		
		
	}

	@Override
	public Page<User> listUsers(Pageable pageable,String filter) {
		if (filter!=""&& filter!=null) {
				//return gameRepository.findById(Long.parseLong(filter.substring(1))).get();
			return userRepository.findAll(pageable ,filter);
		}
		return userRepository.findAll(pageable);
	}

	@Override
	public User getUser(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User changeRole(String role, Long id) {
		User user=userRepository.findById(id).get();
		if (role.equalsIgnoreCase("ADMIN")) {
			user.setRole(Role.ADMIN);
			userRepository.save(user);
			return user;
		}if(role.equalsIgnoreCase("MODER")){
			user.setRole(Role.MODER);
		}else {
			user.setRole(Role.USER);
		}
		
		userRepository.save(user);
		return user;
	}

	@Override
	public User changeStatus(Long id) {
		User user=userRepository.findById(id).get();
		if (user.getStatus().equals("ACTIVE")) {
			user.setStatus("BANNED");	
			
		}else {
			user.setStatus("ACTIVE");
		}
		userRepository.save(user);
		return user;
	}
	@Override
	public boolean updatePassword(String username, PasswordRequest passwordRequest) {
		// Verificar si las credenciales actuales son correctas
        boolean credentialsCorrect = authService.verifyCredentials(username, passwordRequest.getCurrentPassword());
        if (!credentialsCorrect) {
            return false; // Las credenciales actuales son incorrectas, no se puede cambiar la contraseña
        }

        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            user.setPassword(encoder.encode(passwordRequest.getNewPassword()));
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

	@Override
	public boolean updateUser(ResponseUser user) {
		try {
			User user1=userRepository.findByUsername(user.getUsername()).get();
			user1.setProfile(user.getProfile());
			user1.getProfile().setUser(user1);
			userRepository.save(user1);
			return true;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		}
		
		
		
	}

	@Override
	public boolean updatePrivacy(String username, String privacy) {
		User user=userRepository.findByUsername(username).get();
		if(privacy.equalsIgnoreCase("NOBODY")) {
			user.getProfile().setPrivacy(Privacy.NOBODY);
		}
		if(privacy.equalsIgnoreCase("EVERYBODY")) {
			user.getProfile().setPrivacy(Privacy.EVERYBODY);
		}
		if(privacy.equalsIgnoreCase("FRIENDS")) {
			user.getProfile().setPrivacy(Privacy.FRIENDS);
		}
		userRepository.save(user);
		return true;
	}

	@Override
	public void updateBalance(String name, double deposit) {
		try {
			if (deposit>0 && deposit<999) {
			User user=userRepository.findByUsername(name).get();
			user.setBalance(user.getBalance()+deposit);
			userRepository.save(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public boolean checkPassword(String username, String password) {
		boolean credentialsCorrect = authService.verifyCredentials(username, password);
        if (credentialsCorrect) {
            return true;
        }
        else {
        	return false;
        }
	}

	@Override
	public List<UserDTO> getUsers(String filter) {
			List<UserDTO>usersFormated= new ArrayList<>();
		try {
			if (!filter.isEmpty()) {
			List<User> users=userRepository.findAll(filter);
			users.forEach(user->usersFormated.add(new UserDTO(user)));
			
			}
			return usersFormated;
		} catch (Exception e) {
			return usersFormated;
		}
		
		
	}

	@Override
	public UserDTO getUserByUsername(String username, String currentUser) {
		
		User user =userRepository.findByUsername(username).orElse(null);
		if (user != null) {
			UserDTO user1=new UserDTO(user);
			if (user.getProfile().getPrivacy()!=Privacy.NOBODY) {
				List<UserDTO>friends=relationShipService.getFriends(username);
				if (user.getProfile().getPrivacy()==Privacy.EVERYBODY) {
					user1.setProfile(user.getProfile());
					
					List<Order>orders=orderService.getOrderFromUser(username);
					orders.forEach(order->user1.getLibrary().add(order.getGame()));
					user1.setFriends(friends);
					
					
				}else {
					if ( isFriend(friends, currentUser)) {
						user1.setProfile(user.getProfile());
						
						List<Order>orders=orderService.getOrderFromUser(username);
						orders.forEach(order->user1.getLibrary().add(order.getGame()));
						user1.setFriends(friends);
					}
				}
			}
			
			return user1;
			
		}else {
			return null;
		}
		
		
	}
	
	private boolean isFriend(List<UserDTO>friends,String currentUser) {
		boolean isFriend=false;
		
		for (UserDTO userDTO : friends) {
			if (userDTO.getUsername().equalsIgnoreCase(currentUser)) {
				isFriend=true;
			}
			
		}
		
		return isFriend;
		
	}

	@Override
	public void updateUserImage(String username,String imageUrl) {
		User user=userRepository.findByUsername(username).orElse(null);
		if (user!=null) {
			user.getProfile().setPhoto(imageUrl);
			profileRepository.save(user.getProfile());
		}
		
	}

}
