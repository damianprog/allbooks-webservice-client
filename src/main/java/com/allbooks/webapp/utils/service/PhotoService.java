package com.allbooks.webapp.utils.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.BookChild;
import com.allbooks.webapp.entity.Reader;

public interface PhotoService {

	String getEncodedImage(byte[] theEncodedBase64);

	List<Book> encodeBookPics(List<Book> books, int width, int height) throws IOException;

	byte[] convertMultipartImageToBytes(MultipartFile mf) throws IOException;

	File convertMultipartToFile(MultipartFile file) throws IOException;

	byte[] resize(byte[] bookPicBytes, int width, int height);

	void createProfilePhotoForReader(MultipartFile multipartFile, Reader reader) throws IOException;

	void encodeAndResizeBookPhotoInBookChildren(List<? extends BookChild> list,int width,int height);
	
	Reader setResizedAndEncodedPhotoInReader(Reader reader,int width,int height);
	
	void setResizedAndEncodedPhotosInReaders(List<Reader> readers,int width,int height);
	
}
