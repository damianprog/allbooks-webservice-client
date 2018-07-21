package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Review;

@Service
public class ReviewWebserviceImpl implements ReviewWebservice {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.url.name}")
	private String serviceUrlName;

	@Autowired
	private OAuth2RestOperations oAuth2RestOperations;

	private String accessTokenParameter;

	@PostConstruct
	private void initializeAccessTokenField() {
		accessTokenParameter = "?access_token=" + oAuth2RestOperations.getAccessToken().getValue();
	}

	@Override
	public void submitReview(Review review) {

		restTemplate.postForEntity(serviceUrlName + "/reviews" + accessTokenParameter, review, Review.class);

	}

	@Override
	public Review[] getBookReviews(int bookId) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("bookId", String.valueOf(bookId));

		ResponseEntity<Review[]> responseEntity = restTemplate.getForEntity(
				serviceUrlName + "/books/{bookId}/reviews" + accessTokenParameter, Review[].class, params);

		return responseEntity.getBody();
	}

	@Override
	public Review getReviewById(int reviewId) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("reviewId", String.valueOf(reviewId));

		Review review = restTemplate.getForObject(serviceUrlName + "/reviews/{reviewId}" + accessTokenParameter,
				Review.class, params);

		return review;
	}

	@Override
	public void updateReview(Review review) {
		restTemplate.put(serviceUrlName + "/reviews" + accessTokenParameter, review);
	}

	@Override
	public void deleteReviewById(int reviewId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("reviewId", reviewId);

		restTemplate.delete(serviceUrlName + "/reviews/{reviewId}" + accessTokenParameter, params);

	}

	@Override
	public Review[] getReviewsByReaderId(int readerId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);

		ResponseEntity<Review[]> reviews = restTemplate.getForEntity(
				serviceUrlName + "/readers/{readerId}/books/reviews" + accessTokenParameter, Review[].class, params);

		return reviews.getBody();
	}

	@Override
	public void deleteReviewByIdAndReaderId(int reviewId, int readerId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("reviewId", reviewId);
		params.put("readerId", readerId);
		
		restTemplate.delete(serviceUrlName + "/readers/{readerId}/books/reviews/{reviewId}" + accessTokenParameter,params);
		
	}

	@Override
	public Review[] getLatestReaderReviews(int readerId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);

		ResponseEntity<Review[]> responseEntity = restTemplate.getForEntity(
				serviceUrlName + "/readers/{readerId}/reviews/latest" + accessTokenParameter, Review[].class, params);
		
		return responseEntity.getBody();
	}

}
