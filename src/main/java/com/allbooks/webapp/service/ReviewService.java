package com.allbooks.webapp.service;

import java.util.List;
import java.util.Map;

import com.allbooks.webapp.entity.Review;

public interface ReviewService {

	public void submitReview(Review review);

	public List<Review> getBookReviews(int bookId);
	
	public Map<String,Integer> ratingsAndReviewsQuantity(int bookId);
	
	public Review getReviewById(int reviewId);
	
	public void updateReview(Review review);

	public void deleteReviewById(int reviewId);
	
	public List<Review> getReviewsByReaderId(int readerId);
	
	public void dropLike(int reviewId);
	
}
