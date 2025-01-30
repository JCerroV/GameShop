package com.joaquincv.juegos.app.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private static final String SECRET_KEY="194730275639475937507365836493874020574686868";
	
	public String getToken(UserDetails user) {
		return getToken(new HashMap<>(),user);
	}

	private String getToken(Map<String, Object> claims, UserDetails user) {
		return Jwts.builder().setClaims(claims)
				.setSubject(user.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000 * 60 * 60 * 24 * 7))
				.signWith(getKey(), SignatureAlgorithm.HS256)
				.compact();
		
	}
	
	private Key getKey() {
		byte[] keybytes=Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keybytes);
	}

	private Claims getAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
		
	}
	
	public <T> T getClaim(String token,Function<Claims,T> claimsResolver) {
		Claims claims=getAllClaims(token);
		
		return claimsResolver.apply(claims);
	}
	public String getUsernameFromToken(String token) {
		
		return getClaim(token, Claims::getSubject );
	}
	
	private Date getExpiration(String token){
		return getClaim(token, Claims::getExpiration);
	}
	
	private boolean isTokenExpired(String token) {
		return getExpiration(token).before(new Date());
	}

	public boolean isTokenValid(String token, UserDetails details) {
		String username=getUsernameFromToken(token);
		return username.equals(details.getUsername())&& !isTokenExpired(token) ;
	}

}
