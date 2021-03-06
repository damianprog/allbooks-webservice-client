package com.allbooks.webapp.entity;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Reader {

	private int id;

	@Size(max = 20, min = 3, message = "Name must be between 3 and 20 chars")
	private String username;

	@Size(max = 20, min = 6, message = "Password must be between 6 and 20 chars")
	private String password;

	private boolean emailAuthenticated;

	@Email(message = "Invalid Email")
	private String email;

	private Set<Role> roles;

	private byte[] profilePhoto;

	private String encodedProfilePhoto;

	private List<Reader> friends;

	private Details details;

	public Reader() {

	}

	public boolean isEmailAuthenticated() {
		return emailAuthenticated;
	}

	public void setEmailAuthenticated(boolean emailAuthenticated) {
		this.emailAuthenticated = emailAuthenticated;
	}

	public List<Reader> getFriends() {
		return friends;
	}

	public void setFriends(List<Reader> friends) {
		this.friends = friends;
	}

	public byte[] getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(byte[] profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

	public String getEncodedProfilePhoto() {
		return encodedProfilePhoto;
	}

	public void setEncodedProfilePhoto(String encodedProfilePhoto) {
		this.encodedProfilePhoto = encodedProfilePhoto;
	}

	@Override
	public String toString() {
		return "Reader [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + "]";
	}

}
