package com.allbooks.webapp.utils.saver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.entity.ReviewData;

@Component
public class ReviewSaver {

	@Autowired
	private BookService bookService;

	@Autowired
	private ReaderService readerService;

	@Autowired
	private SecurityContextService contextService;
	
	@Autowired
	private ReviewService reviewService;

	@Autowired
	private RatingService ratingService;

	public void save(ReviewData reviewData) {

		int readerId = contextService.getLoggedReaderId();

		Review review = initializeReviewFields(reviewData, readerId);

		reviewService.submitReview(review);

	}

	private Review initializeReviewFields(ReviewData reviewData, int readerId) {
		int bookId = reviewData.getBookId();

		Reader reader = readerService.getReaderById(readerId);

		Book book = bookService.getBookById(bookId);

		Review review = reviewData.getReview();

		review.setBook(book);
		review.setPostingReader(reader);
		review.setRating(ratingService.getReaderRatingObject(readerId, bookId));
		return review;
	}

}
