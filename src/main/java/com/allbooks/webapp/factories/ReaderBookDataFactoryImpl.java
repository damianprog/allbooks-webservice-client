package com.allbooks.webapp.factories;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.CommentData;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.RatingData;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.ReaderBookData;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.entity.ReviewData;

@Component
public class ReaderBookDataFactoryImpl extends BookActionDataObjectFactory {

	@Override
	public ReaderBookData createReaderBookData(ReaderBook readerBook, Map<String, String> params) {

		ReaderBookData readerBookData = new ReaderBookData(readerBook, Integer.parseInt(params.get("bookId")),
				Boolean.valueOf(params.get("isItUpdateReaderBook")));

		return readerBookData;
	}

	@Override
	public RatingData createRatingData(Rating rating, Map<String, String> params) {
		RatingData ratingData = new RatingData(rating, Integer.parseInt(params.get("bookId")),
				Boolean.valueOf(params.get("isItUpdateReaderBook")));

		return ratingData;
	}

	@Override
	public ReviewData createReviewData(Review review, Map<String, String> params) {

		ReviewData reviewData = new ReviewData(review, Integer.parseInt(params.get("bookId")), false);

		return reviewData;
	}

	@Override
	public CommentData createCommentData(Comment comment, Map<String, String> params) {

		CommentData commentData = new CommentData(comment, Integer.valueOf(params.get("bookId")),
				Integer.valueOf(params.get("reviewId")), false);

		return commentData;
	}

}
