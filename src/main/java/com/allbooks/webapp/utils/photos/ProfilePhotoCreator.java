package com.allbooks.webapp.utils.photos;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.utils.service.PhotoService;

@Component
public class ProfilePhotoCreator {

	@Autowired
	private PhotoService photoService;
	
	public Reader createPhotoForReader(MultipartFile mf,Reader reader) throws IOException {
		
		byte[] bytes = photoService.convertMultipartImage(mf, 200, 250);
		
		reader.setProfilePhoto(bytes);
		
		return reader;
	}
	
}
