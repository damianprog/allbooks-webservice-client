package com.allbooks.webapp.utils.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.factories.FileFactory;
import com.allbooks.webapp.utils.photos.ProfilePhotoCreator;

@Service
public class PhotoServiceImpl implements PhotoService {

	@Autowired
	private ProfilePhotoCreator profilePhotoCreator;

	@Autowired
	private FileFactory fileFactory;

	@Override
	public void createProfilePhotoForReader(MultipartFile multipartFile, Reader reader) throws IOException {
		profilePhotoCreator.createPhotoForReader(multipartFile, reader);
	}

	@Override
	public Reader createDefaultPhotoForReader(Reader reader) {
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("images/regularProfileImage.jpg").getFile());
		
		byte[] defaultProfilePhotoBytes = fileFactory.fileToBytes(file);
		
		reader.setProfilePhoto(defaultProfilePhotoBytes);
		
		return reader;
	}

}
