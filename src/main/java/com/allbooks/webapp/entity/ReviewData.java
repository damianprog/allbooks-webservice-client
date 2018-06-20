package com.allbooks.webapp.entity;

public class ReviewData{

	private Review review;

	private int bookId;

	public ReviewData(Review review,int bookId) {
		
		this.review = review;
		this.bookId = bookId;
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
	
}
