package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Rating;

@Service
public class RatingWebserviceImpl implements RatingWebservice {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.url.name}")
	private String serviceUrlName;

	@Override
	public Rating getReaderRatingObject(int bookId, int readerId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);
		params.put("bookId", bookId);

		Rating rating = restTemplate.getForObject(serviceUrlName + "/readers/{readerId}/books/{bookId}/ratings",
				Rating.class, params);

		return rating;
	}

	@Override
	public Rating submitRating(Rating rating) {

		return restTemplate.postForObject(serviceUrlName + "/ratings", rating,Rating.class);

	}

	@Override
	public Rating[] getBookRatings(int bookId) {

		Map<String, String> params = new HashMap<>();
		params.put("bookId", String.valueOf(bookId));

		ResponseEntity<Rating[]> responseEntity = restTemplate.getForEntity(serviceUrlName + "/books/{bookId}/ratings",
				Rating[].class, params);
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
		
		return restTemplate.getForObject(serviceUrlName + "/ratings/{ratingId}", Rating.class,params);
		
	}

}
