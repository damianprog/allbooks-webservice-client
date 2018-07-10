package com.allbooks.webapp.utils.readerbook;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.enumeration.ShelvesStates;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.utils.service.PhotoService;

@Component
public class ReaderBooksForMyBooksGetter {
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private ReaderBookService readerBookService;
	
	public Page<ReaderBook> getPreparedReaderBooks(int readerId,ShelvesStates shelvesStates,int page){
		
		Page<ReaderBook> readerBooks;
		
		if(shelvesStates == null)
			readerBooks = readerBookService.getReaderBooksPages(readerId,page);
		else
			readerBooks = readerBookService.getReaderBooksByShelvesPages(readerId,shelvesStates,page);
		
		for (ReaderBook tempReaderBook : readerBooks) {
			tempReaderBook.setOverallRating(ratingService.getOverallRating(tempReaderBook.getBook().getId()));
			byte[] resizedPhoto = getResizedPhoto(tempReaderBook.getBookPic());
			tempReaderBook.setEncodedBookPic(photoService.getEncodedImage(resizedPhoto));
		}
		
		
		return readerBooks;
	}
	
	private byte[] getResizedPhoto(byte[] photo) {
		
		try {
			return photoService.resize(photo, 100, 160);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return photo;
		
	}
	
}
