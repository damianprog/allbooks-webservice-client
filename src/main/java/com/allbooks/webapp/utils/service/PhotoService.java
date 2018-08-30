package com.allbooks.webapp.utils.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.allbooks.webapp.entity.Reader;

public interface PhotoService {

	void createProfilePhotoForReader(MultipartFile multipartFile, Reader reader) throws IOException;

	Reader createDefaultPhotoForReader(Reader reader);
}
