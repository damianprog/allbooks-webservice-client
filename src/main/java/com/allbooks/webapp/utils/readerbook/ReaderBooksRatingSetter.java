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
	
	public void update(Rating rating) {
		
		ReaderBook readerBook = readerBookService.getReaderBook(rating.getBook().getId(), rating.getReader().getId());
		
		if((readerBook != null) && (readerBook.getReaderRating() == null)) {
			readerBook.setReaderRating(rating);
			readerBookService.saveReaderBook(readerBook);
		}
	}
	
}
