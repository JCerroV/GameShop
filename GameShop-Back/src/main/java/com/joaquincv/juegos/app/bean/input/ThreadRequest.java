package com.joaquincv.juegos.app.bean.input;

public class ThreadRequest {
	
	private String title;
	
	private String content;
	
	public ThreadRequest() {
		// TODO Auto-generated constructor stub
	}
	
	

	public ThreadRequest(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	

}
