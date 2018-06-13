package com.allbooks.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Details;
import com.allbooks.webapp.entity.ProfilePhoto;
import com.allbooks.webapp.webservice.ProfileWebservice;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileWebservice profileWebservice;

	@Override
	public Details getDetails(int readerId) {

		return profileWebservice.getDetails(readerId);
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

	

}
