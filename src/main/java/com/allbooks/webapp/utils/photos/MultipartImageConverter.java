package com.allbooks.webapp.utils.photos;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.allbooks.webapp.factories.FileFactory;

@Component
public class MultipartImageConverter {

	@Autowired
	private MultipartToFile multipartToFile;
	
	@Autowired
	private FileFactory fileFactory;

	public byte[] convert(MultipartFile mf) {

		File convFile = multipartToFile.convert(mf);

		return fileFactory.fileToBytes(convFile);
		
	}
	

}
