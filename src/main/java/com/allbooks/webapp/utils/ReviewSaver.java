package com.allbooks.webapp.utils;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.entity.ReviewData;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReviewService;

@Component
public class ReviewSaver {

	@Autowired
	private BookService bookService;

	@Autowired
	private ReaderService readerService;

	@Autowired
	private HttpSession session;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private RatingService ratingService;

	public void save(ReviewData reviewData) {

		int readerId = (int) session.getAttribute("readerId");

		int bookId = reviewData.getBookId();

		Reader reader = readerService.getReaderById(readerId);

		Book book = bookService.getBook(bookId);

		Review review = reviewData.getReview();

		review.setBook(book);
		review.setReader(reader);
		review.setRating(ratingService.getReaderRatingObject(readerId, bookId));

		reviewService.submitReview(review);

	}

}
