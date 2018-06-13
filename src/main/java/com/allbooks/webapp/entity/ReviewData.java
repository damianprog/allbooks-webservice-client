package com.allbooks.webapp.entity;

public class ReviewData extends BookActionDataObject{

	private Review review;

	private int bookId;

	private boolean isItUpdate;

	public ReviewData(Review review,int bookId,boolean isItUpdate) {
		
		this.review = review;
		this.bookId = bookId;
		this.isItUpdate = isItUpdate;
		
	}
	
	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
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
