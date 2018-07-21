package com.allbooks.webapp.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Review implements ReaderPost, BookChild {

	private int id;

	private String text;

	private String title;

	private List<Like> likes;

	private Reader postingReader;

	private Rating rating;

	private Book book;

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public Reader getPostingReader() {
		return postingReader;
	}

	public void setPostingReader(Reader reader) {
		this.postingReader = reader;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", text=" + text + ", title=" + title + ", likes=" + likes + ", postingReader="
				+ postingReader + ", rating=" + rating + ", book=" + book + "]";
	}

}
