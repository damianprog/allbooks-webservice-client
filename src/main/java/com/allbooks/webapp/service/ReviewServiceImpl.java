package com.allbooks.webapp.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.utils.Sorter;
import com.allbooks.webapp.utils.webservice.UtilsWebservice;
import com.allbooks.webapp.webservice.ReviewWebservice;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private Sorter sorter;

	@Autowired
	private ReviewWebservice reviewWebservice;

	@Autowired
	private RatingService ratingService;

	@Override
	public void submitReview(Review review) {

		reviewWebservice.submitReview(review);
	}

	@Override
	public List<Review> getBookReviews(int bookId) {

		List<Review> reviewList = Arrays.asList(reviewWebservice.getBookReviews(bookId));

		if (reviewList != null)
			sorter.sortReviewsDescending(reviewList);

		return reviewList;
	}

	// external method for counting stuff??
	@Override
	public Map<String, Integer> ratingsAndReviewsQuantity(int bookId) {

		Rating[] ratings = ratingService.getBookRatings(bookId);

		Review[] reviews = reviewWebservice.getBookReviews(bookId);

		Map<String, Integer> map = new HashMap<>();
		map.put("ratings", ratings.length);
		map.put("reviews", reviews.length);

		return map;
	}

	@Override
	public Review getReviewById(int reviewId) {

		return reviewWebservice.getReviewById(reviewId);

	}

	@Override
	public void updateReview(Review review) {

		reviewWebservice.updateReview(review);

	}

	@Override
	public void deleteReviewById(int reviewId) {

		reviewWebservice.deleteReviewById(reviewId);
	}

	@Override
	public List<Review> getReviewsByReaderId(int readerId) {

		List<Review> reviews = Arrays.asList(reviewWebservice.getReviewsByReaderId(readerId));
		reviews.sort(Comparator.comparingInt(Review::getId).reversed());

		return reviews;
	}

}
