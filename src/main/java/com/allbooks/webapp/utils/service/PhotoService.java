package com.allbooks.webapp.utils.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;

public interface PhotoService {

	String getEncodedImage(byte[] theEncodedBase64);

	List<Book> encodeBookPics(List<Book> books, int width, int height) throws IOException;

	byte[] convertMultipartImage(MultipartFile mf, int width, int height) throws IOException;

	File convertMultipartToFile(MultipartFile file) throws IOException;

	byte[] resize(byte[] bookPicBytes, int width, int height);

	Reader createProfilePhotoForReader(MultipartFile multipartFile, Reader reader) throws IOException;

	void encodeAndResizeBookPhotoInReaderBooks(List<ReaderBook> readerBooksList,int width,int height);
	
}
