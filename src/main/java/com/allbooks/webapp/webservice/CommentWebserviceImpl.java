package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Comment;

@Service
public class CommentWebserviceImpl implements CommentWebservice {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.url.name}")
	private String serviceUrlName;

	@Override
	public void submitComment(Comment comment) {
		restTemplate.postForObject(serviceUrlName + "/comments", comment, Comment.class);

	}

	@Override
	public Comment[] getReviewComments(int reviewId) {

		Map<String, String> params = new HashMap<>();
		params.put("reviewId", String.valueOf(reviewId));

		ResponseEntity<Comment[]> responseEntity = restTemplate
				.getForEntity(serviceUrlName + "/reviews/{reviewId}/comments", Comment[].class, params);

		return responseEntity.getBody();
	}

	@Override
	public Comment[] getCommentsByReaderIdAndBookId(int readerId, int bookId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);
		params.put("bookId", bookId);

		ResponseEntity<Comment[]> responseEntity = restTemplate.getForEntity(
				serviceUrlName + "/readers/{readerId}/books/{bookId}/reviews/comments", Comment[].class, params);
		
		return responseEntity.getBody();

	}

}