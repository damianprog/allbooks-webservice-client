package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Review;

@Service
public class ReviewWebserviceImpl implements ReviewWebservice {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.url.name}")
	private String serviceUrlName;

	@Override
	public void submitReview(Review review) {

		restTemplate.postForEntity(serviceUrlName + "/reviews", review, Review.class);

	}

	@Override
	public Review[] getBookReviews(int bookId) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("bookId", String.valueOf(bookId));

		ResponseEntity<Review[]> responseEntity = restTemplate.getForEntity(serviceUrlName + "/books/{bookId}/reviews",
				Review[].class, params);

		return responseEntity.getBody();
	}

	@Override
	public Review getReviewById(int reviewId) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("reviewId", String.valueOf(reviewId));

		Review review = restTemplate.getForObject(serviceUrlName + "/reviews/{reviewId}", Review.class, params);

		return review;
	}

	@Override
	public void updateReview(Review review) {
		restTemplate.put(serviceUrlName + "/reviews", review);
	}

	@Override
	public void deleteReviewById(int reviewId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("reviewId", reviewId);

		restTemplate.delete(serviceUrlName + "/reviews/{reviewId}", params);

	}
	
	@Override
	public Review[] getReviewsByReaderId(int readerId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);

		ResponseEntity<Review[]> reviews = restTemplate
				.getForEntity(serviceUrlName + "/readers/{readerId}/books/reviews", Review[].class, params);

		return reviews.getBody();
	}

}
