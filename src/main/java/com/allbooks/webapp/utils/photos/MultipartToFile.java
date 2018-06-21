package com.allbooks.webapp.utils.photos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.allbooks.webapp.factories.FileFactory;

@Component
public class MultipartToFile {

	@Autowired
	private FileFactory fileFactory;
	
	public File convert(MultipartFile multipartFile) throws IOException {
		File convFile = fileFactory.multipartFileGetOriginalFileName(multipartFile);
		convFile.createNewFile();
		FileOutputStream fos = fileFactory.createFileOutputStream(convFile);
		fos.write(multipartFile.getBytes());
		fos.close();
		return convFile;
	}
	
}
