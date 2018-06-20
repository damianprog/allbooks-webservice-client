package com.allbooks.webapp.webservice;

import com.allbooks.webapp.entity.Comment;

public interface CommentWebservice {

	public void submitComment(Comment comment);
	
	public Comment[] getReviewComments(int reviewId);

	public Comment[] getCommentsByReaderIdAndBookId(int readerId, int bookId);

	public Comment getCommentById(int commentId);
	
}
