package com.joaquincv.juegos.app.models.entities;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "games")
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private String author;
	
	@Column(length = 10000)
	private String description;
	
	@Column(scale = 2)
	private double price;
	
	private boolean status;
	
	private String image;
	
	private double valuate;
	
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
	@JoinTable(name = "game_categories", joinColumns = @JoinColumn(name="game_id",referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name="category_id", referencedColumnName = "id"))
    private List<Category> categories;
	
	private boolean deleted;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	
	public Game() {
		deleted=false;
		createdAt=new Date();
	}
	public Game(String title,String author,String description,double price,List<Category>categories,String image) {
		
		this.title = title;
		this.author = author;
		this.description = description;
		this.price = price;
		this.image = image;
		this.valuate=0;
		this.status=false;
		this.deleted=false;
		this.createdAt=new Date();
	}
	
	public Game(String title, String author, String description, double price, boolean status,
			String image, double valuate) {
		this.title = title;
		this.author = author;
		this.description = description;
		this.price = price;
		this.status = status;
		this.image = image;
		this.valuate = valuate;
		
	}

	
	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getValuate() {
		return valuate;
	}

	public void setValuate(double valuate) {
		this.valuate = valuate;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public void setCreatedAt(Date date) {
		this.createdAt=date;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", title=" + title + ", author=" + author + ", description=" + description
				+ ", price=" + price + ", status=" + status + ", image=" + image + ", valuate=" + valuate + "]";
	}
	
}
