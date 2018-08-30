package com.allbooks.webapp.utils.model;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.readerbook.ReaderBookAndRatingModelCreator;

@Component
public class BookModelCreator {

	@Autowired
	private ModelMapFactory modelMapFactory;
	
	@Autowired
	private RatingService ratingService;

	@Autowired
	private ReaderBookAndRatingModelCreator readerBookAndRatingModelCreator;

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private BookService bookService;

	public ModelMap create(int bookId) {
		
		ModelMap modelMap = modelMapFactory.createInstance();
		
		Book book = bookService.getBookById(bookId);

		modelMap.addAllAttributes(readerBookAndRatingModelCreator.createModel(bookId));
		modelMap.addAttribute("quotesSplit", Arrays.asList(book.getBookQuotes().split("/")));
		modelMap.addAttribute("quantity", reviewService.ratingsAndReviewsQuantity(bookId));
		modelMap.addAttribute("overallRating", ratingService.getOverallRating(bookId));
		modelMap.addAttribute("bookReviews", reviewService.getBookReviews(bookId));
		modelMap.addAttribute("book", book);
		
		return modelMap;
	}
	
}
