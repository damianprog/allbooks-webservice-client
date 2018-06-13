package com.allbooks.webapp.webservice;

import com.allbooks.webapp.entity.Friends;

public interface FriendsWebservice {

	public void saveFriends(Friends friends);
	
	public Friends getFriendsById(int friendsIdInt);

	public void deleteFriends(int friendsIdInt, int reader2Id);

	public Friends[] getAllReaderFriends(int id);

	public Friends getFriendsByReader1IdAndReader2Id(int reader1Id, int reader2Id);
	
}
