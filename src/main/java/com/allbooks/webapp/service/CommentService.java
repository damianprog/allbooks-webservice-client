package com.allbooks.webapp.service;

import java.util.List;

import com.allbooks.webapp.entity.Comment;

public interface CommentService {

	public void submitComment(Comment comment);

	public List<Comment> getReviewComments(int reviewId);
	
}
