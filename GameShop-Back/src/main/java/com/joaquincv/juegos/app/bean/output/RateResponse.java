package com.joaquincv.juegos.app.bean.output;

import com.joaquincv.juegos.app.models.entities.GameRate;

public class RateResponse {
	
	private Long id;
	
	private int rate;
	
	public RateResponse() {}

	public RateResponse(GameRate gameRate) {
		super();
		this.id = gameRate.getId();
		this.rate = gameRate.getRate();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
	
	

}
