package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Like;

@Service
public class LikeWebserviceImpl implements LikeWebservice{

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
	public Like getLikeByReviewIdAndReaderId(int reviewId, int readerId) {
		
		Map<String,Integer> params = new HashMap<>();
		params.put("reviewId", reviewId);
		params.put("readerId", readerId);
		
		return restTemplate.getForObject(serviceUrlName + "/review/{reviewId}/likes/readers/{readerId}" + accessTokenParameter, Like.class,params);
		
	}

	@Override
	public void deleteLikeById(int likeId) {

		Map<String,Integer> params = new HashMap<>();
		params.put("likeId", likeId);
		
		restTemplate.delete(serviceUrlName + "/likes/{likeId}" + accessTokenParameter,params);
		
	}

}
