package com.allbooks.webapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Details;
import com.allbooks.webapp.entity.Friends;
import com.allbooks.webapp.entity.ProfilePics;
import com.allbooks.webapp.entity.Reader;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	RestTemplate restTemplate;

	@Override
	public Details getDetails(int readerId) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerId", String.valueOf(readerId));

		Details details = restTemplate.getForObject("http://localhost:9000/readers/{readerId}/details", Details.class,
				params);

		return details;
	}

	@Override
	public Reader getReaderById(int readerId) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerId", String.valueOf(readerId));

		Reader reader = restTemplate.getForObject("http://localhost:9000/readers/{readerId}", Reader.class, params);

		return reader;
	}

	@Override
	public int getReaderId(String readerLogin) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerLogin", String.valueOf(readerLogin));

		Reader reader = restTemplate.getForObject("http://localhost:9000/readers/logins/{readerLogin}", Reader.class,
				params);

		return reader.getId();
	}

	@Override
	public void saveFriends(Friends friends) {
		restTemplate.put("http://localhost:9000/friends", friends);
	}

	@Override
	public Friends areTheyFriends(String reader1, String reader2) {

		boolean friends = false;
		int reader1Id = getReaderId(reader1);
		int reader2Id = getReaderId(reader2);

		List<Friends> friendsList = getAllReaderFriends(reader1Id);
		Friends friendship = null;

		for (Friends f : friendsList) {
			if ((f.getReader1() == reader2Id) || (f.getReader2() == reader2Id)) {
				friendship = f;
			}
		}

		return friendship;
	}

	@Override
	public List<Friends> getFriendsInvites(int id) {
		
		List<Friends> readerFriends = getAllReaderFriends(id);
		List<Friends> friendsRequests = new ArrayList<>();

		for (Friends f : readerFriends) {
			if ((f.getAccept().equals("false")) && (f.getReader1()==id))
				friendsRequests.add(f);
		}

		return friendsRequests;
	}

	@Override
	public Friends getFriendsById(int friendsIdInt) {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", String.valueOf(friendsIdInt));

		Friends friends = restTemplate.getForObject("http://localhost:9000/friends/{id}", Friends.class,
				params);
		
		return friends;
	}

	@Override
	public void deleteFriends(int friendsIdInt) {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("friendsId", String.valueOf(friendsIdInt));
		
		restTemplate.delete("http://localhost:9000/friends/{friendsId}",params);

	}

	@Override
	public List<Friends> getAllReaderFriends(int reader1) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("readerId", String.valueOf(reader1));

		ResponseEntity<Friends[]> response = restTemplate
				.getForEntity("http://localhost:9000/readers/{readerId}/friends", Friends[].class, params);
		Friends[] friendsArray = response.getBody();

		List<Friends> friendsList = new ArrayList<>();

		for (Friends f : friendsArray)
			friendsList.add(f);

		return friendsList;
	}

	public List<Friends> getReaderFriends(int readerId) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerId", String.valueOf(readerId));

		ResponseEntity<Friends[]> response = restTemplate
				.getForEntity("http://localhost:9000/readers/{readerId}/friends", Friends[].class, params);
		Friends[] friendsArray = response.getBody();

		List<Friends> friendsList = new ArrayList<>();

		for (Friends f : friendsArray) {
			if (f.getAccept().equals("true"))
				friendsList.add(f);
		}

		return friendsList;
	}

	@Override
	public void saveDetails(Details details) {
		System.out.println("DETAILS FROM SAVE DETAILS" + details.toString());
		restTemplate.put("http://localhost:9000/details", details);
		
	}

	@Override
	public void saveProfilePics(ProfilePics profilePics,int readerId) {
		restTemplate.postForObject("http://localhost:9000/profilepics", profilePics, ProfilePics.class);
		
	}

	@Override
	public ProfilePics getProfilePics(int readerId) {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("readerId", String.valueOf(readerId));
		
		ProfilePics profilePics = restTemplate.getForObject("http://localhost:9000/readers/{readerId}/profilepics", ProfilePics.class,
				params);
		
		return profilePics;
	}

	@Override
	public void deleteReader(int readerId) {
		
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("readerId", readerId);
		
		restTemplate.delete("http://localhost:9000/readers/{readerId}",params);
	}

}
