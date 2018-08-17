package com.allbooks.webapp.utils.bookactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.RatingData;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.readerbook.ReaderBookRatingUpdater;

@Component
public class RatingSaver {

	@Autowired
	private SecurityContextService contextService;

	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private ReaderService readerService;

	@Autowired
	private BookService bookService;
	
	@Autowired
	private ReaderBookRatingUpdater readerBookRatingUpdater;
	
	@Autowired
	private CommentsRatingUpdater commentsRatingUpdater;
	
	public void save(RatingData ratingData) {

		int readerId = contextService.getLoggedReaderId();

		Rating rating = ratingData.getRating();

		Reader reader = readerService.getReaderById(readerId);

		Book book = bookService.getBookById(ratingData.getBookId());

		rating.setReader(reader);
		rating.setBook(book);

		Rating savedRating = ratingService.submitRating(rating);

		commentsRatingUpdater.update(savedRating);
		readerBookRatingUpdater.update(savedRating);
		

	}

}
