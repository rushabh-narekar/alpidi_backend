package com.alpidi.payload.response;

import java.util.List;

public class JwtResponse {
	private String accessToken;
	private String type = "Bearer";
	private String id;
	private String firstname;
	private String lastname;
	private String email;
	private String role;
	//private List<String> roles;

	public JwtResponse(String accessToken, String id, String role, String firstname,String lastname,String email) {
		this.accessToken = accessToken;
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.role = role;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

//	public List<String> getRoles() {
//		return roles;
//	}
}

