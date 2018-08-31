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

	private Page<ReaderBook> readerBooks;

	private int readerId;

	private ShelvesState shelvesState;

	private int page;

	public Page<ReaderBook> getPreparedReaderBooks(int readerId, ShelvesState shelvesState, int page) {

		initializeThisFields(readerId, shelvesState, page);

		initializeReaderBooksPage();

		setOverallRatingInReaderBooks();

		return readerBooks;
	}

	private void initializeReaderBooksPage() {
		if (shelvesState == null)
			readerBooks = readerBookService.getReaderBooksPages(readerId, page);
		else
			readerBooks = readerBookService.getReaderBooksByShelvesPages(readerId, shelvesState, page);
	}

	private void initializeThisFields(int readerId, ShelvesState shelvesState, int page) {
		this.readerId = readerId;
		this.shelvesState = shelvesState;
		this.page = page;
	}

	private void setOverallRatingInReaderBooks() {
		for (ReaderBook tempReaderBook : readerBooks.getContent())
			tempReaderBook.setOverallRating(ratingService.getOverallRating(tempReaderBook.getBook().getId()));
	}

}
