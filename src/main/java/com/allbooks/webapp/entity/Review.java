package com.allbooks.webapp.entity;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Review {

	private int id;

	private String text;

	private String title;

	private String bookTitle;

	private int likes;

	private String readerLogin;

	private Reader reader;

	private int readerIdentity;

	private Rating rating;

	private int bookId;

	private List<Comment> comments;

	@JsonIgnore
	private byte[] profilePic;

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public int getReaderIdentity() {
		return readerIdentity;
	}

	public void setReaderIdentity(int readerIdentity) {
		this.readerIdentity = readerIdentity;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

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

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", text=" + text + ", title=" + title + ", bookTitle=" + bookTitle + ", likes="
				+ likes + ", readerLogin=" + readerLogin + ", reader=" + reader + ", readerIdentity=" + readerIdentity
				+ ", rating=" + rating + ", bookId=" + bookId + ", comments=" + comments + ", profilePic="
				+ Arrays.toString(profilePic) + "]";
	}

	
	
}
