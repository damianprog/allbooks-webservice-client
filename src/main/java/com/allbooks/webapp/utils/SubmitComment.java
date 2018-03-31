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
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.ReaderService;

@Component
public class SubmitComment {

	@Autowired
	ReaderService readerService;

	@Autowired
	BookService bookService;

	public void submit(Comment comment, Map<String, String> params) {

		Reader reader = readerService.getReaderByUsername(params.get("readerLogin"));
		int readerId = reader.getId();

		comment.setReaderId(readerId);
		comment.setReaderLogin(reader.getUsername());

		Rating rating = bookService.getReaderRatingObject(params.get("bookName"), reader.getUsername());

		if (rating.getId() != 0)
			comment.setRating(rating);

		Review review = bookService.getReviewById(Integer.valueOf(params.get("reviewId")));
		List<Comment> comments = review.getComments();

		if (comments == null)
			comments = new ArrayList<Comment>();

		comments.add(comment);
		review.setComments(comments);

		bookService.updateReview(review);

	}

}
