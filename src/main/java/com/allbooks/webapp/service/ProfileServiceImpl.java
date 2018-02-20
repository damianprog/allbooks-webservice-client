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
import com.allbooks.webapp.entity.Pending;
import com.allbooks.webapp.entity.ProfilePics;
import com.allbooks.webapp.entity.Reader;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	RestTemplate restTemplate;

	String url = "http://localhost:9000";

	@Override
	public Details getDetails(int readerId) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerId", String.valueOf(readerId));

		Details details = restTemplate.getForObject(url + "/readers/{readerId}/details", Details.class, params);

		return details;
	}

	@Override
	public Reader getReaderById(int readerId) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerId", String.valueOf(readerId));

		Reader reader = restTemplate.getForObject(url + "/readers/{readerId}", Reader.class, params);

		return reader;
	}

	@Override
	public int getReaderId(String readerLogin) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerLogin", String.valueOf(readerLogin));

		Reader reader = restTemplate.getForObject(url + "/readers/logins/{readerLogin}", Reader.class, params);

		return reader.getId();
	}

	@Override
	public void saveFriends(Friends friends) {
		restTemplate.put("http://localhost:9000/friends", friends);
	}

	@Override
	public Friends areTheyFriends(String reader1, String reader2) {

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
	public List<Pending> getFriendsInvites(int readerId) {

		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("readerId", readerId);

		ResponseEntity<Pending[]> response = restTemplate.getForEntity(url + "/readers/{readerId}/friends/pending",
				Pending[].class, params);

		Pending[] pendingArray = response.getBody();

		List<Pending> readerPendings = new ArrayList<>();

		for (Pending p : pendingArray) {
			readerPendings.add(p);
		}

		return readerPendings;
	}

	@Override
	public Friends getFriendsById(int friendsIdInt) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("id", String.valueOf(friendsIdInt));

		Friends friends = restTemplate.getForObject(url + "/friends/{id}", Friends.class, params);

		return friends;
	}

	@Override
	public void deleteFriends(int reader1,int reader2) {

		Map<String, Integer> params = new HashMap<>();
		params.put("reader1Id",reader1);
		params.put("reader2Id",reader2);

		restTemplate.delete(url + "/readers/{reader1Id}/friends/{reader2Id}", params);

	}

	@Override
	public List<Friends> getAllReaderFriends(int reader1) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("readerId", reader1);

		ResponseEntity<Friends[]> response = restTemplate.getForEntity(url + "/readers/{readerId}/friends",
				Friends[].class, params);
		Friends[] friendsArray = response.getBody();

		List<Friends> friendsList = new ArrayList<>();

		for (Friends f : friendsArray)
			friendsList.add(f);

		return friendsList;
	}

	public List<Reader> getReaderFriends(int readerId) {

		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("readerId", readerId);

		ResponseEntity<Friends[]> response = restTemplate.getForEntity(url + "/readers/{readerId}/friends",
				Friends[].class, params);
		Friends[] friendsArray = response.getBody();

		List<Reader> friends = new ArrayList<>();
		
		for (Friends f : friendsArray) {
			if (f.getReader1() == readerId)
				params.put("readerId", f.getReader2());
			
			else if(f.getReader2() == readerId) 
				params.put("readerId", f.getReader1());
			
			Reader reader = restTemplate.getForObject(url + "/readers/{readerId}",
					Reader.class, params);
			friends.add(reader);
			
			reader.getUsername();
		}

		return friends;
	}

	@Override
	public void saveDetails(Details details) {
		System.out.println("DETAILS FROM SAVE DETAILS" + details.toString());
		restTemplate.put(url + "/details", details);

	}

	@Override
	public void saveProfilePics(ProfilePics profilePics, int readerId) {
		restTemplate.postForObject(url + "/profilepics", profilePics, ProfilePics.class);

	}

	@Override
	public ProfilePics getProfilePics(int readerId) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerId", String.valueOf(readerId));

		ProfilePics profilePics = restTemplate.getForObject(url + "/readers/{readerId}/profilepics", ProfilePics.class,
				params);

		return profilePics;
	}

	@Override
	public void deleteReader(int readerId) {

		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("readerId", readerId);

		restTemplate.delete(url + "/readers/{readerId}", params);
	}

	@Override
	public void savePending(Pending pending) {
		restTemplate.postForObject(url + "/friends/pending", pending, Pending.class);
	}

	@Override
	public Pending getPending(String reader1, String reader2) {

		int reader1Id = getReaderId(reader1);
		int reader2Id = getReaderId(reader2);

		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("reader1", reader1Id);
		params.put("reader2", reader2Id);

		Pending pending = restTemplate.getForObject(url + "/readers/{reader1}/friends/{reader2}/pending", Pending.class,
				params);

		return pending;
	}

	@Override
	public void deletePending(int pendingIdInt) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("pendingId", pendingIdInt);

		restTemplate.delete(url + "/pending/{pendingId}", params);

	}

}
