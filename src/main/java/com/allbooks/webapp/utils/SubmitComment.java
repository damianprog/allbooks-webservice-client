package com.allbooks.webapp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReviewService;

@Component
public class SubmitComment {

	@Autowired
	private ReaderService readerService;

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private RatingService ratingService;
	
	public void submit(Comment comment, Map<String, String> params) {

		Reader reader = readerService.getReaderByUsername(params.get("readerLogin"));
		int readerId = reader.getId();

		comment.setReaderId(readerId);
		comment.setReaderLogin(reader.getUsername());

		int bookId = Integer.parseInt(params.get("bookId"));
		
		Rating rating = ratingService.getReaderRatingObject(readerId,bookId);

		if (rating.getId() != 0)
			comment.setRating(rating);

		Review review = reviewService.getReviewById(Integer.valueOf(params.get("reviewId")));
		List<Comment> comments = review.getComments();

		if (comments == null)
			comments = new ArrayList<Comment>();

		comments.add(comment);
		review.setComments(comments);

		reviewService.updateReview(review);

	}

}
