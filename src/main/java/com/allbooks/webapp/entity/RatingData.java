package com.allbooks.webapp.entity;

public class RatingData extends BookActionDataObject{

	private Rating rating;

	private int bookId;

	private boolean isItUpdate;
	
	public RatingData(Rating rating, int bookId, boolean isItUpdate) {
		this.rating = rating;
		this.bookId = bookId;
		this.isItUpdate = isItUpdate;
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

	public boolean isItUpdate() {
		return isItUpdate;
	}

	public void setIsItUpdate(boolean isItUpdate) {
		this.isItUpdate = isItUpdate;
	}

}
