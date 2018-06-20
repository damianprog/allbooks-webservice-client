package com.allbooks.webapp.entity;

public class CommentData{

	private Comment comment;

	private int bookId;

	private int reviewId;

	public CommentData(Comment comment, int bookId, int reviewId) {
		this.comment = comment;
		this.bookId = bookId;
		this.reviewId = reviewId;
	}
	
	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

}
