package com.allbooks.webapp.service;

import java.util.List;

import com.allbooks.webapp.entity.Friends;
import com.allbooks.webapp.entity.Pending;
import com.allbooks.webapp.entity.Reader;

public interface FriendsService {

	public void saveFriends(Friends friends);

	public boolean areTheyFriends(int reader1Id, int reader2Id);

	public Friends getFriendsById(int friendsIdInt);

	public void deleteFriends(int friendsIdInt, int reader2Id);

	public List<Friends> getAllReaderFriends(int id);

	public List<Reader> getReaderFriends(int id);
	
	public Friends getFriendsByReader1IdAndReader2Id(int reader1Id, int reader2Id);

}
