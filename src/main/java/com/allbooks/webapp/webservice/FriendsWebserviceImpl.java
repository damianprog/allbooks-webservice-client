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

import com.allbooks.webapp.entity.Friends;

@Service
public class FriendsWebserviceImpl implements FriendsWebservice {

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
	public void saveFriends(Friends friends) {
		restTemplate.put("http://localhost:9000/friends" + accessTokenParameter, friends);

	}

	@Override
	public Friends getFriendsById(int friendsIdInt) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", friendsIdInt);

		return restTemplate.getForObject(serviceUrlName + "/friends/{id}" + accessTokenParameter, Friends.class,
				params);
	}

	@Override
	public void deleteFriends(int reader1, int reader2) {
		Map<String, Integer> params = new HashMap<>();
		params.put("reader1Id", reader1);
		params.put("reader2Id", reader2);

		restTemplate.delete(serviceUrlName + "/readers/{reader1Id}/friends/readers/{reader2Id}" + accessTokenParameter,
				params);

	}

	@Override
	public Friends[] getAllReaderFriends(int id) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("readerId", id);

		ResponseEntity<Friends[]> response = restTemplate.getForEntity(
				serviceUrlName + "/readers/{readerId}/friends" + accessTokenParameter, Friends[].class, params);
		return response.getBody();
	}

	@Override
	public Friends getFriendsByReader1IdAndReader2Id(int reader1Id, int reader2Id) {
		Map<String, Integer> params = new HashMap<>();
		params.put("reader1Id", reader1Id);
		params.put("reader2Id", reader2Id);

		return restTemplate.getForObject(
				serviceUrlName + "/readers/{reader1Id}/friends/readers/{reader2Id}" + accessTokenParameter,
				Friends.class, params);

	}

}
