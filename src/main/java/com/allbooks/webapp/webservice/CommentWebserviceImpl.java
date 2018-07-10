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

import com.allbooks.webapp.entity.Comment;

@Service
public class CommentWebserviceImpl implements CommentWebservice {

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
	public void submitComment(Comment comment) {
		restTemplate.put(serviceUrlName + "/comments" + accessTokenParameter, comment, Comment.class);

	}

	@Override
	public Comment[] getReviewComments(int reviewId) {

		Map<String, String> params = new HashMap<>();
		params.put("reviewId", String.valueOf(reviewId));

		ResponseEntity<Comment[]> responseEntity = restTemplate
				.getForEntity(serviceUrlName + "/reviews/{reviewId}/comments" + accessTokenParameter, Comment[].class, params);

		return responseEntity.getBody();
	}

	@Override
	public Comment[] getCommentsByReaderIdAndBookId(int readerId, int bookId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);
		params.put("bookId", bookId);

		ResponseEntity<Comment[]> responseEntity = restTemplate.getForEntity(
				serviceUrlName + "/readers/{readerId}/books/{bookId}/reviews/comments" + accessTokenParameter, Comment[].class, params);
		
		return responseEntity.getBody();

	}

	@Override
	public Comment getCommentById(int commentId) {
		
		Map<String, Integer> params = new HashMap<>();
		params.put("commentId", commentId);
		
		return restTemplate.getForObject(serviceUrlName + "/comments/{commentId}" + accessTokenParameter, Comment.class,params);
		
	}

}
