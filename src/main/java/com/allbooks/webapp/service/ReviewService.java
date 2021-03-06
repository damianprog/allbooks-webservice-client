package com.allbooks.webapp.service;

import java.util.List;
import java.util.Map;

import com.allbooks.webapp.entity.ReaderPost;
import com.allbooks.webapp.entity.Review;

public interface ReviewService {

	void submitReview(Review review);

	List<ReaderPost> getBookReviews(int bookId);

	Map<String, Integer> ratingsAndReviewsQuantity(int bookId);

	Review getReviewById(int reviewId);

	void updateReview(Review review);

	void deleteReviewById(int reviewId);

	List<Review> getReviewsByReaderId(int readerId);

	List<Review> getLatestReaderReviews(int readerId);
	
}
