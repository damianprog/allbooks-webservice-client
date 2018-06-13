package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Friends;

@Service
public class FriendsWebserviceImpl implements FriendsWebservice {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.url.name}")
	private String serviceUrlName;

	@Override
	public void saveFriends(Friends friends) {
		restTemplate.put("http://localhost:9000/friends", friends);

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

		restTemplate.delete(serviceUrlName + "/readers/{reader1Id}/friends/readers/{reader2Id}", params);

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
	public Friends getFriendsByReader1IdAndReader2Id(int reader1Id, int reader2Id) {
		Map<String, Integer> params = new HashMap<>();
		params.put("reader1Id", reader1Id);
		params.put("reader2Id", reader2Id);
		
		return restTemplate.getForObject(serviceUrlName + "/readers/{reader1Id}/friends/readers/{reader2Id}", Friends.class, params);
		
	}

}
