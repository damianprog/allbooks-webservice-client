package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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

	@Override
	public Details getDetails(int id) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("readerId", id);

		Details details = restTemplate.getForObject(serviceUrlName + "/readers/{readerId}/details", Details.class,
				params);

		return details;
	}

	@Override
	public void saveDetails(Details details) {
		restTemplate.put(serviceUrlName + "/details", details);

	}

	@Override
	public void saveProfilePics(ProfilePhoto profilePics, int readerId) {
		restTemplate.postForObject(serviceUrlName + "/profilepics", profilePics, ProfilePhoto.class);

	}

	@Override
	public ProfilePhoto getProfilePicsByReaderId(int id) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("readerId", id);

		return restTemplate.getForObject(serviceUrlName + "/readers/{readerId}/profilepics", ProfilePhoto.class,
				params);
	}

}
