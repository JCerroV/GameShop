package com.joaquincv.juegos.app.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "profile")
public class Profile {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "surnames", nullable = false)
	private String surnames;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "photo")
	private String photo;
	
	private String biografy;
	
	@Enumerated(EnumType.STRING)
	private Privacy privacy;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
	@JsonBackReference
	private User user;

	
	public Profile() {};
	
	public Profile(String name, String surnames, User user) {
		super();
		this.name = name;
		this.surnames = surnames;
		this.user = user;
		this.privacy=Privacy.FRIENDS;
		this.photo="http://localhost:8080/images/perfil.png";
	}

	public Profile(String name, String surnames, String phone, String location) {
		super();
		this.name = name;
		this.surnames = surnames;
		this.phone = phone;
		this.location = location;
		this.privacy=Privacy.FRIENDS;
		this.photo="http://localhost:8080/images/perfil.png";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurnames() {
		return surnames;
	}

	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getBiografy() {
		return biografy;
	}

	public void setBiografy(String biografy) {
		this.biografy = biografy;
	}

	public Privacy getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Privacy privacy) {
		this.privacy = privacy;
	}

	
	
	
}
