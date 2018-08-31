package com.allbooks.webapp.utils.readerbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.service.ReaderBookService;

@Component
public class ReaderBooksRatingSetter {

	@Autowired
	private ReaderBookService readerBookService;
	
	private int bookId,readerId;
	
	private Rating rating;
	
	private ReaderBook readerBook;
	
	public void set(Rating rating) {
		
		initializeThisFields(rating);
		
		if(isReaderBookNeedingUpdate())
			updateReaderBookRating();
	}

	private boolean isReaderBookNeedingUpdate() {
		return (readerBook != null) && (readerBook.getReaderRating() == null);
	}

	private void updateReaderBookRating() {
		readerBook.setReaderRating(rating);
		readerBookService.saveReaderBook(readerBook);
	}
	
	private void initializeThisFields(Rating rating) {
		this.bookId = rating.getBook().getId();
		this.readerId = rating.getReader().getId();
		this.rating = rating;
		this.readerBook = readerBookService.getReaderBook(bookId, readerId);
	}
	
}
