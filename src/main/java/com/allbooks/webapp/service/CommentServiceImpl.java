package com.allbooks.webapp.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.webservice.CommentWebservice;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentWebservice commentWebservice;
	
	@Override
	public void submitComment(Comment comment) {
		commentWebservice.submitComment(comment);
	}

	@Override
	public List<Comment> getReviewComments(int reviewId) {

		List<Comment> commentsList = Arrays.asList(commentWebservice.getReviewComments(reviewId));

		commentsList.sort(Comparator.comparingInt(Comment::getId).reversed());

		return commentsList;
	}
	
}
