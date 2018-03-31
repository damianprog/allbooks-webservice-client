package com.allbooks.webapp.utils.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.allbooks.webapp.entity.Book;

public interface PhotoService {

	public String getEncodedImage(byte[] theEncodedBase64);

	public List<Book> encodeBookPics(List<Book> books) throws IOException;

	public byte[] convertMultipartImage(MultipartFile mf, int width, int height) throws IOException;

	public File convertMultipartToFile(MultipartFile file) throws IOException;

	public byte[] resize(byte[] bookPicBytes, int width, int height) throws IOException;

}
