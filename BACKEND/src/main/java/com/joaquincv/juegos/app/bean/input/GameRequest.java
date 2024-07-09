package com.joaquincv.juegos.app.bean.input;



import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.joaquincv.juegos.app.models.entities.Category;

public class GameRequest {

	private Long id;
	private String title;
	private String author;
	private String description;
	private double price;
	private MultipartFile photo;
	private List<Category> categories;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String titulo) {
		this.title = titulo;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public MultipartFile getPhoto() {
		return photo;
	}
	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public GameRequest(Long id, String titulo, String author, String description, double price, MultipartFile photo,
			List<Category> categories) {
		super();
		this.id = id;
		this.title = titulo;
		this.author = author;
		this.description = description;
		this.price = price;
		this.photo = photo;
		this.categories = categories;
	}
	
	public GameRequest() {
		categories=new ArrayList<>();
	}
}
