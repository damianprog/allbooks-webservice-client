package com.allbooks.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Review {

	private int id;

	private String text;

	private String title;

	private int likes;

	private String readerLogin;

	private int readerRating;

	private int readerId;

	private int bookId;
	
	@JsonIgnore //may cause problems while updating/saving review(?)
	private byte[] profilePic;

	public byte[] getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(byte[] profilePic) {
		this.profilePic = profilePic;
	}

	public Review() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getReaderId() {
		return readerId;
	}

	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReaderLogin() {
		return readerLogin;
	}

	public void setReaderLogin(String readerLogin) {
		this.readerLogin = readerLogin;
	}

	public int getReaderRating() {
		return readerRating;
	}

	public void setReaderRating(int readerRating) {
		this.readerRating = readerRating;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

}
