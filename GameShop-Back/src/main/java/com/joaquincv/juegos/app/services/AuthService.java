package com.joaquincv.juegos.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.joaquincv.juegos.app.config.AuthResponse;
import com.joaquincv.juegos.app.config.JwtService;
import com.joaquincv.juegos.app.config.LoginRequest;
import com.joaquincv.juegos.app.config.RegisterRequest;
import com.joaquincv.juegos.app.models.entities.Role;
import com.joaquincv.juegos.app.models.entities.User;
import com.joaquincv.juegos.app.repository.UserRepository;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private JwtService jwtService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	public AuthResponse login(LoginRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
		UserDetails details=userRepository.findByUsername(request.getUsername()).orElseThrow();
		String token=jwtService.getToken(details);
		return new AuthResponse(token);
	
	}
	
	public AuthResponse register(RegisterRequest request) {
		User user = new User(request.getUsername(),encoder.encode(request.getPassword()),request.getEmail());
		
		
		return new AuthResponse(jwtService.getToken(user));
	}
	
	public boolean verifyCredentials(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return true; // Las credenciales son correctas
        } catch (AuthenticationException e) {
        	e.printStackTrace();
            return false; // Las credenciales son incorrectas
        }
    }
	
}
