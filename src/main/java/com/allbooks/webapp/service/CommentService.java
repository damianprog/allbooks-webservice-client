package com.allbooks.webapp.service;

import java.util.List;

import com.allbooks.webapp.entity.Comment;

public interface CommentService {

	public void submitComment(Comment comment);

	public List<Comment> getCommentsByReaderIdAndBookId(int readerId,int bookId);
	
	public List<Comment> getReviewComments(int reviewId);

	public Comment getCommentById(int commentId);

	
}
