package com.allbooks.webapp.utils.service;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.BookChild;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.factories.FileFactory;
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

	@Autowired
	private FileFactory fileFactory;
	
	@Override
	public String getEncodedImage(byte[] theEncodedBase64) {

		return base64Encoder.getEncodedImage(theEncodedBase64);
	}

	@Override
	public List<Book> encodeBookPics(List<Book> books, int width, int height) throws IOException {

		return bookPicsEncoder.encode(books, width, height);
	}

	@Override
	public byte[] convertMultipartImageToBytes(MultipartFile mf) throws IOException {

		return multipartImageConverter.convert(mf);

	}

	@Override
	public File convertMultipartToFile(MultipartFile file) throws IOException {

		return multipartToFile.convert(file);
	}

	@Override
	public byte[] resize(byte[] bookPicBytes, int width, int height) {

		return resizePhoto.resize(bookPicBytes, width, height);
	}

	@Override
	public void createProfilePhotoForReader(MultipartFile multipartFile, Reader reader) throws IOException {
		profilePhotoCreator.createPhotoForReader(multipartFile, reader);
	}

	@Override
	public List<? extends BookChild> encodeAndResizeBookPhotoInBookChildren(List<? extends BookChild> reviewsList, int width, int height) {
		for (BookChild bc : reviewsList) {
			byte[] resizedPhoto = resizePhoto.resize(bc.getBook().getBookPhoto(), width, height);
			bc.getBook().setEncodedBookPhoto(getEncodedImage(resizedPhoto));
		}

		return reviewsList;
	}

	@Override
	public void setResizedAndEncodedPhotosInReaders(List<Reader> readers, int width, int height) {

		Iterator<Reader> iter = readers.iterator();

		iter.forEachRemaining(r -> {
			r.setEncodedProfilePhoto(getEncodedImage(resize(r.getProfilePhoto(), width, height)));
		});

	}

	@Override
	public Reader setResizedAndEncodedPhotoInReader(Reader reader, int width, int height) {

		reader.setEncodedProfilePhoto(getEncodedImage(resize(reader.getProfilePhoto(),width,height)));
		
		return reader;
	}

	public void createDefaultPhotoForReader(Reader reader) {
		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("images/regularProfileImage.jpg").getFile());
		
		byte[] defaultProfilePhotoBytes = fileFactory.fileToBytes(file);
		
		reader.setProfilePhoto(defaultProfilePhotoBytes);
		
	}

}
