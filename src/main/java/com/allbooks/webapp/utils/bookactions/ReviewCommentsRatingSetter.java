package com.allbooks.webapp.utils.bookactions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.service.CommentService;

@Component
public class ReviewCommentsRatingSetter {

	@Autowired
	private CommentService commentService;

	private Rating rating;

	private int readerId;

	private int bookId;

	public void updateRatingInReaderReviewComments(Rating rating) {

		initializeThisFields(rating);

		List<Comment> comments = commentService.getCommentsByReaderIdAndBookId(readerId, bookId);

		for (Comment comment : comments)
			setRatingInCommentIfCommentRatingIsNull(comment);

	}

	private void initializeThisFields(Rating rating) {
		this.readerId = rating.getReader().getId();
		this.bookId = rating.getBook().getId();
		this.rating = rating;
	}
	
	private void setRatingInCommentIfCommentRatingIsNull(Comment comment) {
		if (comment.getRating() == null)
			updateCommentRating(comment);
	}

	private void updateCommentRating(Comment comment) {
		comment.setRating(rating);
		commentService.submitComment(comment);
	}

}
