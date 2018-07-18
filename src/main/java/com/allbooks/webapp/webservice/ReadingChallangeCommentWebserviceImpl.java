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

import com.allbooks.webapp.entity.ReadingChallangeComment;

@Service
public class ReadingChallangeCommentWebserviceImpl implements ReadingChallangeCommentWebservice {

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
	public void save(ReadingChallangeComment readingChallangeComment) {
		restTemplate.put(serviceUrlName + "/readingChallangeComments" + accessTokenParameter,
				readingChallangeComment);
	}

	@Override
	public ReadingChallangeComment[] getReadingChallangeCommentsByChallangeReaderId(int challangeReaderId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("challangeReaderId", challangeReaderId);

		ResponseEntity<ReadingChallangeComment[]> re = restTemplate.getForEntity(serviceUrlName
				+ "/readers/{challangeReaderId}/readingChallange/readingChallangeComments" + accessTokenParameter,
				ReadingChallangeComment[].class, params);

		return re.getBody();

	}

	@Override
	public ReadingChallangeComment getCommentById(int commentId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("commentId", commentId);

		return restTemplate.getForObject(
				serviceUrlName + "/readingChallangeComments/{commentId}" + accessTokenParameter,
				ReadingChallangeComment.class, params);

	}

	@Override
	public void deleteCommentById(int commentId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("commentId", commentId);
		
		restTemplate.delete(serviceUrlName + "/readingChallangeComments/{commentId}" + accessTokenParameter,params);
	}

}
