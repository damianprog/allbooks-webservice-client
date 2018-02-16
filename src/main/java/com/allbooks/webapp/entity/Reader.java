package com.allbooks.webapp.entity;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Reader {

	private int id;

	private String username;

	private String password;

	private String email;

	private Set<Role> roles;

	private ProfilePics profilePic;

	private Details details;

	private List<Review> reviews;

	private List<Rating> ratings;

	private List<ReaderBook> readerBooks;

	public ProfilePics getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(ProfilePics profilePic) {
		this.profilePic = profilePic;
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
