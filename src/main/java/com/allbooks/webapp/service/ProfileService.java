package com.allbooks.webapp.service;

import com.allbooks.webapp.entity.Details;
import com.allbooks.webapp.entity.ProfilePhoto;

public interface ProfileService {

	public Details getDetails(int id);

	public void saveDetails(Details details);

	public void saveProfilePics(ProfilePhoto profilePics, int readerId);

	public ProfilePhoto getProfilePicsByReaderId(int id);

}
