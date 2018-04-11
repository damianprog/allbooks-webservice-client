package com.allbooks.webapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Details;
import com.allbooks.webapp.entity.Friends;
import com.allbooks.webapp.entity.Pending;
import com.allbooks.webapp.entity.ProfilePhoto;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.webservice.ProfileWebservice;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	ReaderService readerService;

	@Autowired
	ProfileWebservice profileWebservice;

	@Override
	public Details getDetails(int readerId) {

		return profileWebservice.getDetails(readerId);
	}

	@Override
	public void saveFriends(Friends friends) {
		profileWebservice.saveFriends(friends);
	}

	@Override
	public boolean areTheyFriends(String reader1, String reader2) {

		int reader1Id = readerService.getReaderId(reader1);
		int reader2Id = readerService.getReaderId(reader2);

		List<Friends> friendsList = getAllReaderFriends(reader1Id);
		Friends friendship = null;

		for (Friends f : friendsList) {
			if ((f.getReader1() == reader2Id) || (f.getReader2() == reader2Id)) {
				friendship = f;
			}
		}

		if (friendship != null)
			return true;
		else
			return false;
	}

	@Override
	public List<Pending> getFriendsInvites(int readerId) {

		return Arrays.asList(profileWebservice.getReaderPendings(readerId));
	}

	@Override
	public Friends getFriendsById(int friendsIdInt) {

		return profileWebservice.getFriendsById(friendsIdInt);
	}

	@Override
	public void deleteFriends(int reader1, int reader2) {

		profileWebservice.deleteFriends(reader1, reader2);

	}

	@Override
	public List<Friends> getAllReaderFriends(int reader1) {

		return Arrays.asList(profileWebservice.getAllReaderFriends(reader1));

	}

	public List<Reader> getReaderFriends(int readerId) {

		// TODO refactor it

		Friends[] friendsArray = profileWebservice.getAllReaderFriends(readerId);

		int readersFriendId=0;
		
		List<Reader> friends = new ArrayList<>();

		for (Friends f : friendsArray) {
			if (f.getReader1() == readerId)
				readersFriendId = f.getReader2();
			else if (f.getReader2() == readerId)
				readersFriendId = f.getReader1();

			Reader reader = readerService.getReaderById(readersFriendId);
			friends.add(reader);

			reader.getUsername();
		}

		return friends;
	}

	@Override
	public void saveDetails(Details details) {
		profileWebservice.saveDetails(details);

	}

	@Override
	public void saveProfilePics(ProfilePhoto profilePics, int readerId) {
		profileWebservice.saveProfilePics(profilePics, readerId);

	}

	@Override
	public ProfilePhoto getProfilePicsByReaderId(int readerId) {

		return profileWebservice.getProfilePicsByReaderId(readerId);
	}

	@Override
	public void savePending(Pending pending) {
		profileWebservice.savePending(pending);
	}

	@Override
	public Pending getPending(String reader1, String reader2) {

		int reader1Id = readerService.getReaderId(reader1);
		int reader2Id = readerService.getReaderId(reader2);

		return profileWebservice.getReadersPending(reader1Id, reader2Id);

	}

	@Override
	public void deletePending(int pendingIdInt) {
		profileWebservice.deletePending(pendingIdInt);

	}

	@Override
	public boolean checkPending(String name, String username) {

		Pending pending = getPending(name, username);

		if (pending == null)
			return false;
		else
			return true;
	}

}
