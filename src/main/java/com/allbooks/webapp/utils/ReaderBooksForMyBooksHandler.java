package com.allbooks.webapp.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.utils.service.PhotoService;

@Component
public class ReaderBooksForMyBooksHandler {
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private ReaderBookService readerBookService;
	
	public List<ReaderBook> prepareReaderBooks(int readerId){
		
		List<ReaderBook> readerBooks = readerBookService.getReaderBooks(readerId);

		for (ReaderBook tempReaderBook : readerBooks) {
			tempReaderBook.setOverallRating(ratingService.getOverallRating(tempReaderBook.getBook().getId()));
			tempReaderBook.setEncodedBookPic(photoService.getEncodedImage(tempReaderBook.getBookPic()));
		}
		
		
		return readerBooks;
	}
	
}
