package com.allbooks.webapp.utils.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.photos.component.Base64Encoder;
import com.allbooks.webapp.photos.component.BookPicsEncoder;
import com.allbooks.webapp.photos.component.MultipartImageConverter;
import com.allbooks.webapp.photos.component.MultipartToFile;
import com.allbooks.webapp.photos.component.ResizePhoto;

@Service
public class PhotoServiceImpl implements PhotoService {

	@Autowired
	Base64Encoder base64Encoder;

	@Autowired
	BookPicsEncoder bookPicsEncoder;

	@Autowired
	MultipartImageConverter multipartImageConverter;

	@Autowired
	MultipartToFile multipartToFile;

	@Autowired
	ResizePhoto resizePhoto;

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

		return multipartImageConverter.convertMultipartImage(mf, width, height);

	}

	@Override
	public File convertMultipartToFile(MultipartFile file) throws IOException {

		return multipartToFile.convert(file);
	}

	@Override
	public byte[] resize(byte[] bookPicBytes, int width, int height) throws IOException {

		return resizePhoto.resize(bookPicBytes, width, height);
	}

}
