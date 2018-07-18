package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.ReadingChallange;

@Service
public class ReadingChallangeWebserviceImpl implements ReadingChallangeWebservice{

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
	public void saveReadingChallange(ReadingChallange readingChallange) {

		restTemplate.postForObject(serviceUrlName + "/readingChallanges" + accessTokenParameter,readingChallange ,ReadingChallange.class );
		
	}

	@Override
	public ReadingChallange getReadingChallangeById(int id) {
		Map<String,Integer> params = new HashMap<>();
		params.put("readingChallangeId", id);
		
		return restTemplate.getForObject(serviceUrlName + "/readingChallanges/{readingChallangeId}" + accessTokenParameter, ReadingChallange.class,params);
		
	}

	@Override
	public ReadingChallange getReadingChallangeByReaderId(int readerId) {
		Map<String,Integer> params = new HashMap<>();
		params.put("readerId", readerId);
		
		return restTemplate.getForObject(serviceUrlName + "/readers/{readerId}/readingChallanges" + accessTokenParameter, ReadingChallange.class,params);
		
	}

}
