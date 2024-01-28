package com.example.project.dto;

public class UserDto {
	private Long id;
	private String email;
	private String name;
	private String phoneNumber;
	private String password;
	private boolean active;
	private String role;

	public UserDto(Long id, String email, String name, String phoneNumber, boolean active, String role,String password) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.active = active;
		this.role = role;
		this.password=password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
