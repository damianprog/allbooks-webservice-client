package com.allbooks.webapp.photos.component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MultipartImageConverter {

	@Autowired
	MultipartToFile multipartToFile;
	
	@Autowired
	ResizePhoto resizePhoto;
	
	public byte[] convert(MultipartFile mf, int width, int height) throws IOException {

		File convFile = multipartToFile.convert(mf);

		return resizePhoto.resize(Files.readAllBytes(convFile.toPath()), width, height);

	}

	

}
