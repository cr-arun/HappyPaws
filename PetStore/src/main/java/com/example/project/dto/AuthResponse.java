package com.example.project.dto;

public class AuthResponse {
	private UserDto user;
	private String token;

	public AuthResponse(UserDto user, String token) {
		super();
		this.user = user;
		this.token = token;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
