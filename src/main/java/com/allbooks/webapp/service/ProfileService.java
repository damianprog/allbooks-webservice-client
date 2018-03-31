package com.allbooks.webapp.service;

import java.util.List;

import com.allbooks.webapp.entity.Details;
import com.allbooks.webapp.entity.Friends;
import com.allbooks.webapp.entity.PasswordToken;
import com.allbooks.webapp.entity.Pending;
import com.allbooks.webapp.entity.ProfilePics;
import com.allbooks.webapp.entity.Reader;

public interface ProfileService {

	public Details getDetails(int id);

	public void saveFriends(Friends friends);

	public boolean areTheyFriends(String reader1, String reader2);

	public List<Pending> getFriendsInvites(int id);

	public Friends getFriendsById(int friendsIdInt);

	public void deleteFriends(int friendsIdInt, int reader2Id);

	public List<Friends> getAllReaderFriends(int id);

	public List<Reader> getReaderFriends(int id);

	public void saveDetails(Details details);

	public void saveProfilePics(ProfilePics profilePics, int readerId);

	public ProfilePics getProfilePics(int id);

	public void savePending(Pending pending);

	public Pending getPending(String name, String username);
	
	public boolean checkPending(String name, String username);

	public void deletePending(int pendingIdInt);

	
}
