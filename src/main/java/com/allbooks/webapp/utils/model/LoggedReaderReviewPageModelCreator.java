package com.allbooks.webapp.utils.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.RatingService;

@Component
public class LoggedReaderReviewPageModelCreator {

	@Autowired
	private SecurityContextService contextService;

	@Autowired
	private RatingService ratingService;

	@Autowired
	private ModelMapFactory modelMapFactory;

	private ModelMap modelMap;

	private Book book;

	public ModelMap createModel(Review review) {

		initializeThisFields(review);

		initializeModelMapIfReaderIsAuthenticated();

		return modelMap;
	}

	private void initializeThisFields(Review review) {
		this.modelMap = modelMapFactory.createInstance();
		this.book = review.getBook();
	}
	
	private void initializeModelMapIfReaderIsAuthenticated() {

		if (contextService.isReaderAuthenticated())
			initializeAuthenticatedReaderModelMap();
	}

	private void initializeAuthenticatedReaderModelMap() {

		int loggedReaderId = contextService.getLoggedReaderId();
		Rating readerRating = ratingService.getReaderRatingObject(loggedReaderId, book.getId());

		if (readerRating != null)
			modelMap.addAttribute("loggedReaderRating", readerRating);
		else
			modelMap.addAttribute("loggedReaderRating", new Rating());
	}

}
