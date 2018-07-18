package com.allbooks.webapp.utils.readerbook;

import java.util.List;

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

	public Page<ReaderBook> getPreparedReaderBooks(int readerId, ShelvesStates shelvesStates, int page) {

		Page<ReaderBook> readerBooks;

		if (shelvesStates == null)
			readerBooks = readerBookService.getReaderBooksPages(readerId, page);
		else
			readerBooks = readerBookService.getReaderBooksByShelvesPages(readerId, shelvesStates, page);

		photoService.encodeAndResizeBookPhotoInReaderBooks(readerBooks.getContent(), 100, 160);

		for (ReaderBook tempReaderBook : readerBooks) 
			tempReaderBook.setOverallRating(ratingService.getOverallRating(tempReaderBook.getBook().getId()));

		return readerBooks;
	}

}
