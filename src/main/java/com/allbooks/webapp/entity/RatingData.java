package com.allbooks.webapp.entity;

public class RatingData{

	private Rating rating;

	private int bookId;

	
	public RatingData(Rating rating, int bookId) {
		this.rating = rating;
		this.bookId = bookId;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

}
