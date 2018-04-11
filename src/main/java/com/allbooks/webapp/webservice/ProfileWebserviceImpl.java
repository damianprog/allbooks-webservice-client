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
	RestTemplate restTemplate;

	@Value("${service.url.name}")
	String serviceUrlName;

	@Override
	public Details getDetails(int id) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("readerId", id);

		Details details = restTemplate.getForObject(serviceUrlName + "/readers/{readerId}/details", Details.class,
				params);

		return details;
	}

	@Override
	public void saveFriends(Friends friends) {
		restTemplate.put("http://localhost:9000/friends", friends);

	}

	@Override
	public Pending[] getReaderPendings(int id) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("readerId", id);

		ResponseEntity<Pending[]> response = restTemplate
				.getForEntity(serviceUrlName + "/readers/{readerId}/friends/pending", Pending[].class, params);

		return response.getBody();
	}

	@Override
	public Friends getFriendsById(int friendsIdInt) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", friendsIdInt);

		return restTemplate.getForObject(serviceUrlName + "/friends/{id}", Friends.class, params);
	}

	@Override
	public void deleteFriends(int reader1, int reader2) {
		Map<String, Integer> params = new HashMap<>();
		params.put("reader1Id", reader1);
		params.put("reader2Id", reader2);

		restTemplate.delete(serviceUrlName + "/readers/{reader1Id}/friends/{reader2Id}", params);

	}

	@Override
	public Friends[] getAllReaderFriends(int id) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("readerId", id);

		ResponseEntity<Friends[]> response = restTemplate.getForEntity(serviceUrlName + "/readers/{readerId}/friends",
				Friends[].class, params);
		return response.getBody();
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

	@Override
	public void savePending(Pending pending) {
		restTemplate.postForObject(serviceUrlName + "/friends/pending", pending, Pending.class);
	}

	@Override
	public Pending getReadersPending(int reader1Id, int reader2Id) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("reader1", reader1Id);
		params.put("reader2", reader2Id);

		return restTemplate.getForObject(serviceUrlName + "/readers/{reader1}/friends/{reader2}/pending", Pending.class,
				params);
	}

	@Override
	public void deletePending(int pendingIdInt) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("pendingId", pendingIdInt);

		restTemplate.delete(serviceUrlName + "/pending/{pendingId}", params);
	}

}
