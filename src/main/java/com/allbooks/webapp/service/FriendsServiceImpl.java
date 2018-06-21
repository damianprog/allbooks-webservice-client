package com.allbooks.webapp.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Friends;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.utils.reader.ReaderFriendsInitializer;
import com.allbooks.webapp.webservice.FriendsWebservice;

@Service
public class FriendsServiceImpl implements FriendsService {

	@Autowired
	private FriendsWebservice friendsWebservice;

	@Autowired
	private ReaderFriendsInitializer readerFriendsInitializer;

	@Override
	public void saveFriends(Friends friends) {
		friendsWebservice.saveFriends(friends);
	}

	@Override
	public boolean areTheyFriends(int reader1Id, int reader2Id) {

		Friends friends = getFriendsByReader1IdAndReader2Id(reader1Id, reader2Id);
		
		if(friends == null)
			return false;
		else
			return true;
		
	}

	@Override
	public Friends getFriendsByReader1IdAndReader2Id(int reader1Id, int reader2Id) {
		return friendsWebservice.getFriendsByReader1IdAndReader2Id(reader1Id,reader2Id);
	}
	
	@Override
	public Friends getFriendsById(int friendsIdInt) {

		return friendsWebservice.getFriendsById(friendsIdInt);
	}

	@Override
	public void deleteFriends(int reader1, int reader2) {

		friendsWebservice.deleteFriends(reader1, reader2);

	}

	@Override
	public List<Friends> getAllReaderFriends(int reader1) {

		return Arrays.asList(friendsWebservice.getAllReaderFriends(reader1));

	}

	@Override
	public List<Reader> getReaderFriends(int readerId) {

		return readerFriendsInitializer.getReaderFriends(readerId);

	}

}
