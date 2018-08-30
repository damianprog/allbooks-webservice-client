package com.allbooks.webapp.factories;

import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Comment;

@Component
public class CommentFactoryImpl extends CommentFactory{

	@Override
	public Comment createReviewCommentInstance() {
		return new Comment();
	}
	
}
