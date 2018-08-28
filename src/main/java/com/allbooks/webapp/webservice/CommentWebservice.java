package com.allbooks.webapp.webservice;

import com.allbooks.webapp.entity.Comment;

public interface CommentWebservice {

	void submitComment(Comment comment);

	Comment[] getReviewComments(int reviewId);

	Comment[] getCommentsByReaderIdAndBookId(int readerId, int bookId);

	Comment getCommentById(int commentId);

	void deleteCommentById(int commentId);

}
