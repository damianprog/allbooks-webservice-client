package com.allbooks.webapp.webservice;

import com.allbooks.webapp.entity.Details;
import com.allbooks.webapp.entity.ProfilePhoto;

public interface ProfileWebservice {

	public Details getDetails(int id);

	public void saveDetails(Details details);

	public void saveProfilePics(ProfilePhoto profilePics, int readerId);

	public ProfilePhoto getProfilePicsByReaderId(int id);

}
