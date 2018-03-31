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

	private boolean enabled;

	@Email(message = "Invalid Email")
	private String email;

	private Set<Role> roles;

	private ProfilePics profilePics;

	private List<Reader> friends;

	private Details details;

	private List<Review> reviews;

	private List<Rating> ratings;

	private List<ReaderBook> readerBooks;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Reader> getFriends() {
		return friends;
	}

	public void setFriends(List<Reader> friends) {
		this.friends = friends;
	}

	public ProfilePics getProfilePics() {
		return profilePics;
	}

	public void setProfilePics(ProfilePics profilePics) {
		this.profilePics = profilePics;
	}

	public List<ReaderBook> getReaderBooks() {
		return readerBooks;
	}

	public void setReaderBooks(List<ReaderBook> readerBooks) {
		this.readerBooks = readerBooks;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

	public Reader() {

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

	@Override
	public String toString() {
		return "Reader [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + "]";
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
