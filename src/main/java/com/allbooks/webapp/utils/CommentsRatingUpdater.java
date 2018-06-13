package com.allbooks.webapp.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.service.CommentService;

@Component
public class CommentsRatingUpdater {

	@Autowired
	private CommentService commentService;

	public void update(Rating rating) {

		List<Comment> comments = commentService.getCommentsByReaderIdAndBookId(rating.getReader().getId(),
				rating.getBook().getId());

		for(Comment comment : comments) {
			
			if(comment.getRating() == null) {
				comment.setRating(rating);
				commentService.submitComment(comment);
			}
		}
		
	}

}
