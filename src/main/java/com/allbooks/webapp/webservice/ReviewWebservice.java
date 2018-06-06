package com.allbooks.webapp.webservice;

import com.allbooks.webapp.entity.Review;

public interface ReviewWebservice {

	public void submitReview(Review review);

	public Review[] getBookReviews(int bookId);
	
	public Review[] getReviewsByReaderId(int readerId);
	
	public Review getReviewById(int reviewId);
	
	public void deleteReviewById(int reviewId);
	
	public void updateReview(Review review);
	
}
