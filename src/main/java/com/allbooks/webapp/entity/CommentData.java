package com.allbooks.webapp.entity;

public class CommentData extends BookActionDataObject {

	private Comment comment;

	private int bookId;

	private int reviewId;

	private boolean isItUpdate;

	public CommentData(Comment comment, int bookId, int reviewId, boolean isItUpdate) {
		this.comment = comment;
		this.bookId = bookId;
		this.reviewId = reviewId;
		this.isItUpdate = isItUpdate;
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

	public boolean isItUpdate() {
		return isItUpdate;
	}

	public void setIsItUpdate(boolean isItUpdate) {
		this.isItUpdate = isItUpdate;
	}

}
