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

import com.allbooks.webapp.entity.Details;
import com.allbooks.webapp.entity.Friends;
import com.allbooks.webapp.entity.Pending;
import com.allbooks.webapp.entity.ProfilePhoto;

@Service
public class ProfileWebserviceImpl implements ProfileWebservice {

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
	public Details getDetails(int id) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("readerId", id);

		Details details = restTemplate.getForObject(
				serviceUrlName + "/readers/{readerId}/details" + accessTokenParameter, Details.class, params);

		return details;
	}

	@Override
	public void saveDetails(Details details) {
		restTemplate.put(serviceUrlName + "/details" + accessTokenParameter, details);

	}

	@Override
	public void saveProfilePics(ProfilePhoto profilePics, int readerId) {
		restTemplate.postForObject(serviceUrlName + "/profilepics" + accessTokenParameter, profilePics,
				ProfilePhoto.class);

	}

	@Override
	public ProfilePhoto getProfilePicsByReaderId(int id) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("readerId", id);

		return restTemplate.getForObject(serviceUrlName + "/readers/{readerId}/profilepics" + accessTokenParameter,
				ProfilePhoto.class, params);
	}

}
