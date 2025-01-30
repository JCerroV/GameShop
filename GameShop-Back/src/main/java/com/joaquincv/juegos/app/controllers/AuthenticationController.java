package com.joaquincv.juegos.app.controllers;


import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joaquincv.juegos.app.bean.input.PasswordRequest;
import com.joaquincv.juegos.app.bean.output.ResponseUser;
import com.joaquincv.juegos.app.config.AuthResponse;
import com.joaquincv.juegos.app.config.JwtService;
import com.joaquincv.juegos.app.config.LoginRequest;
import com.joaquincv.juegos.app.config.RegisterRequest;
import com.joaquincv.juegos.app.models.entities.User;
import com.joaquincv.juegos.app.repository.UserRepository;
import com.joaquincv.juegos.app.services.AuthService;
import com.joaquincv.juegos.app.services.RelationShipService;
import com.joaquincv.juegos.app.services.UserService;



@RestController
@CrossOrigin("*")
public class AuthenticationController {
	
	@Autowired
	private AuthService authservice;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired 
	RelationShipService relationShipService;

	@PostMapping("/auth/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(authservice.login(loginRequest));
	}
	@PostMapping("/auth/register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
		userService.saveUser(registerRequest);
		return ResponseEntity.ok(authservice.register(registerRequest));
	}
	
//	@PostMapping("/auth/register")
//	public ResponseEntity<boolean> validateToken(@RequestBody RegisterRequest registerRequest) {
//		return ResponseEntity.ok(jwtService.isTokenValid(, null));
//	}
	
	@GetMapping("/actual")
	public ResponseEntity<ResponseUser> getUser(Principal principal) {
		User user=repository.findByUsername(principal.getName()).orElse(null);
		ResponseUser responseUser=new ResponseUser(user);
		responseUser.setFriends(relationShipService.getFriends(user.getUsername()));
		return new ResponseEntity<ResponseUser>(responseUser,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/auth/check-username")
	public boolean checkUser(@RequestParam String username) {
		return repository.existsByUsername(username);	
	}
	@GetMapping("/auth/check-email")
	public boolean checkEmail(@RequestParam String email) {
		return repository.existsByEmail(email);
	}
	
	@PostMapping("/auth/check-password")
	public ResponseEntity<Boolean> checkPassword(Principal principal,@RequestBody String password){
		try {
			
			return new ResponseEntity<Boolean>(userService.checkPassword(principal.getName(),password),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/deposit-money")
	public ResponseEntity<String> updateBalance(Principal principal,@RequestBody double deposit){
		try {
			userService.updateBalance(principal.getName(),deposit);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping(value="profile/{username}/updateData")
	public ResponseEntity<Boolean> updateProfile(@RequestBody ResponseUser user){
		System.out.println("Pasa");
		
		return new ResponseEntity<>(userService.updateUser(user),HttpStatus.ACCEPTED);
	}
	
	@PutMapping("profile/{username}/updatePassword")
	public ResponseEntity<Boolean> updatePassword(@PathVariable String username,@RequestBody PasswordRequest passwordRequest){
		return new ResponseEntity<>(userService.updatePassword(username, passwordRequest),HttpStatus.ACCEPTED);
	}
	
	@PutMapping("profile/{username}/updatePrivacy")
	public ResponseEntity<Boolean> update(@PathVariable String username, @RequestBody String privacy){
		return new ResponseEntity<>(userService.updatePrivacy(username, privacy),HttpStatus.ACCEPTED);
	}
	
	
	
	
}
