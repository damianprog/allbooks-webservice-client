package com.allbooks.webapp.utils.readerbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.enumeration.ShelvesState;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderBookService;

@Component
public class ReaderBooksForMyBooksGetter {

	@Autowired
	private RatingService ratingService;

	@Autowired
	private ReaderBookService readerBookService;

	public Page<ReaderBook> getPreparedReaderBooks(int readerId, ShelvesState shelvesStates, int page) {

		Page<ReaderBook> readerBooks;

		if (shelvesStates == null)
			readerBooks = readerBookService.getReaderBooksPages(readerId, page);
		else
			readerBooks = readerBookService.getReaderBooksByShelvesPages(readerId, shelvesStates, page);

		for (ReaderBook tempReaderBook : readerBooks.getContent()) 
			tempReaderBook.setOverallRating(ratingService.getOverallRating(tempReaderBook.getBook().getId()));

		return readerBooks;
	}

}
