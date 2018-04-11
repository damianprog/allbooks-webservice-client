package com.allbooks.webapp.webservice;

import com.allbooks.webapp.entity.Details;
import com.allbooks.webapp.entity.Friends;
import com.allbooks.webapp.entity.Pending;
import com.allbooks.webapp.entity.ProfilePhoto;

public interface ProfileWebservice {

	public Details getDetails(int id);

	public void saveFriends(Friends friends);

	public Pending[] getReaderPendings(int id);

	public Friends getFriendsById(int friendsIdInt);

	public void deleteFriends(int friendsIdInt, int reader2Id);

	public Friends[] getAllReaderFriends(int id);

	public void saveDetails(Details details);

	public void saveProfilePics(ProfilePhoto profilePics, int readerId);

	public ProfilePhoto getProfilePicsByReaderId(int id);

	public void savePending(Pending pending);

	public Pending getReadersPending(int reader1Id, int reader2Id);

	public void deletePending(int pendingIdInt);

}
