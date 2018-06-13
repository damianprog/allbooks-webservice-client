package com.allbooks.webapp.utils;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.RatingData;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderService;

@Component
public class RatingSaver {

	@Autowired
	private HttpSession session;

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

		int readerId = (int) session.getAttribute("readerId");

		Rating rating = ratingData.getRating();

		Reader reader = readerService.getReaderById(readerId);

		Book book = bookService.getBook(ratingData.getBookId());

		rating.setReader(reader);
		rating.setBook(book);

		Rating savedRating = ratingService.submitRating(rating);

		commentsRatingUpdater.update(savedRating);
		readerBookRatingUpdater.update(savedRating);
		

	}

}
