package com.allbooks.webapp.webservice;

import com.allbooks.webapp.entity.Review;

public interface ReviewWebservice {

	void submitReview(Review review);

	Review[] getBookReviews(int bookId);

	Review[] getReviewsByReaderId(int readerId);

	Review getReviewById(int reviewId);

	void deleteReviewById(int reviewId);

	void updateReview(Review review);

	void deleteReviewByIdAndReaderId(int reviewId, int readerId);

}
