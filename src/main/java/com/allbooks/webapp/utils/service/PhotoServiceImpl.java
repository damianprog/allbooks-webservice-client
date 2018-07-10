package com.allbooks.webapp.utils.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.utils.photos.Base64Encoder;
import com.allbooks.webapp.utils.photos.BookPicsEncoder;
import com.allbooks.webapp.utils.photos.MultipartImageConverter;
import com.allbooks.webapp.utils.photos.MultipartToFile;
import com.allbooks.webapp.utils.photos.ProfilePhotoCreator;
import com.allbooks.webapp.utils.photos.ResizePhoto;

@Service
public class PhotoServiceImpl implements PhotoService {

	@Autowired
	private Base64Encoder base64Encoder;

	@Autowired
	private BookPicsEncoder bookPicsEncoder;

	@Autowired
	private MultipartImageConverter multipartImageConverter;

	@Autowired
	private MultipartToFile multipartToFile;

	@Autowired
	private ResizePhoto resizePhoto;

	@Autowired
	private ProfilePhotoCreator profilePhotoCreator;
	
	@Override
	public String getEncodedImage(byte[] theEncodedBase64) {

		return base64Encoder.getEncodedImage(theEncodedBase64);
	}

	@Override
	public List<Book> encodeBookPics(List<Book> books) throws IOException {

		return bookPicsEncoder.encode(books);
	}

	@Override
	public byte[] convertMultipartImage(MultipartFile mf, int width, int height) throws IOException {

		return multipartImageConverter.convert(mf, width, height);

	}

	@Override
	public File convertMultipartToFile(MultipartFile file) throws IOException {

		return multipartToFile.convert(file);
	}

	@Override
	public byte[] resize(byte[] bookPicBytes, int width, int height) throws IOException {

		return resizePhoto.resize(bookPicBytes, width, height);
	}

	@Override
	public Reader createProfilePhotoForReader(MultipartFile multipartFile,Reader reader) throws IOException {
		return profilePhotoCreator.createPhotoForReader(multipartFile, reader);
	}

}
