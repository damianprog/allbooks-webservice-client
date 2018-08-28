package com.allbooks.webapp.utils.entity;

public class CommentData {

	private String commentText;

	private int bookId;

	private int reviewId;

	public CommentData(String commentText, int bookId, int reviewId) {
		this.commentText = commentText;
		this.bookId = bookId;
		this.reviewId = reviewId;
	}

	public CommentData() {
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
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
