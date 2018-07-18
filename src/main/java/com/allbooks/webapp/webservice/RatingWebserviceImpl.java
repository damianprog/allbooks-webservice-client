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

import com.allbooks.webapp.entity.Rating;

@Service
public class RatingWebserviceImpl implements RatingWebservice {

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
	public Rating getReaderRatingObject(int bookId, int readerId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);
		params.put("bookId", bookId);

		Rating rating = restTemplate.getForObject(
				serviceUrlName + "/readers/{readerId}/books/{bookId}/ratings" + accessTokenParameter, Rating.class,
				params);

		return rating;
	}

	@Override
	public Rating submitRating(Rating rating) {

		return restTemplate.postForObject(serviceUrlName + "/ratings" + accessTokenParameter, rating, Rating.class);

	}

	@Override
	public Rating[] getBookRatings(int bookId) {

		Map<String, String> params = new HashMap<>();
		params.put("bookId", String.valueOf(bookId));

		ResponseEntity<Rating[]> responseEntity = restTemplate.getForEntity(
				serviceUrlName + "/books/{bookId}/ratings" + accessTokenParameter, Rating[].class, params);
		return responseEntity.getBody();

	}

	@Override
	public int getReaderRating(String username, String bookName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Rating getRatingById(int ratingId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("ratingId", ratingId);

		return restTemplate.getForObject(serviceUrlName + "/ratings/{ratingId}" + accessTokenParameter, Rating.class,
				params);

	}

	@Override
	public Rating[] get10LatestRatings() {
		ResponseEntity<Rating[]> responseEntity = restTemplate.getForEntity(
				serviceUrlName + "/ratings/latest" + accessTokenParameter, Rating[].class);
		return responseEntity.getBody();
	}

}
