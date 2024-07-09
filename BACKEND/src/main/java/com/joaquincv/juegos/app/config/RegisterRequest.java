package com.joaquincv.juegos.app.config;

public class RegisterRequest {
	String username;
	String name;
	String lastname;
	String password;
	String email;
	String phone;
	String country;
	
	public RegisterRequest() {}
	
	public RegisterRequest(String username, String name, String lastname, String password, String email, String phone,String country) {
		this.username = username;
		this.name = name;
		this.lastname = lastname;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.country = country;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "RegisterRequest [username=" + username + ", name=" + name + ", lastname=" + lastname + ", password="
				+ password + ", email=" + email + ", phone=" + phone + ", country=" + country + "]";
	}

	
}
