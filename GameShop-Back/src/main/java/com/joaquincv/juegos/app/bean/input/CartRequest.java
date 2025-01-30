package com.joaquincv.juegos.app.bean.input;

public class CartRequest {
	
	private Long gameId;
	private String username;
	public Long getGameId() {
		return gameId;
	}
	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
	public String getUsername() {
		return username;
	}
	public void setUserId(String username) {
		this.username = username;
	}
	public CartRequest(String username,Long gameId ) {
		super();
		this.gameId = gameId;
		this.username = username;
	}
	
	public CartRequest() {}

}
