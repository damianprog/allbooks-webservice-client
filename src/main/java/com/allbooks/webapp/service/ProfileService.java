package com.allbooks.webapp.service;

import java.util.List;

import com.allbooks.webapp.entity.Details;
import com.allbooks.webapp.entity.Friends;
import com.allbooks.webapp.entity.ProfilePics;
import com.allbooks.webapp.entity.Reader;

public interface ProfileService {

	public Details getDetails(int id);

	public Reader getReaderById(int readerId);

	public int getReaderId(String readerLogin);

	public void saveFriends(Friends friends);

	public Friends areTheyFriends(String reader1, String reader2);

	public List<Friends> getFriendsInvites(int id);

	public Friends getFriendsById(int friendsIdInt);

	public void deleteFriends(int friendsIdInt);

	public List<Friends> getAllReaderFriends(int id);

	public List<Friends> getReaderFriends(int id);

	public void saveDetails(Details details);

	public void saveProfilePics(ProfilePics profilePics,int readerId);

	public ProfilePics getProfilePics(int id);

}
