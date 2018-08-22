package com.allbooks.webapp.utils.bookactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.enumeration.ShelvesState;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderBookService;

@Component
public class PostReviewHelper {

	@Autowired
	private ReaderBookService readerBookService;

	@Autowired
	private RatingService ratingService;
	
	public ReaderBook getReaderBook(int readerBookId, ShelvesState shelvesStates) {

		ReaderBook readerBook;

		if (readerBookId == 0) {
			readerBook = new ReaderBook();
			readerBook.setShelvesStates(shelvesStates);
		}
		else {
			readerBook = readerBookService.getReaderBookById(readerBookId);
			readerBook.setShelvesStates(shelvesStates);
		}

		return readerBook;
	}

	public Rating getRating(int ratingId, int rate) {

		Rating rating;

		if (ratingId == 0)
			rating = new Rating(rate);
		else {
			rating = ratingService.getRatingById(ratingId);
			rating.setRate(rate);
		}

		return rating;
	}

}
