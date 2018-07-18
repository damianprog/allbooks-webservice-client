package com.allbooks.webapp.utils.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.RatingService;

@Component
public class LoggedReviewPageModelCreator {
	
	@Autowired
	private SecurityContextService contextService;
	
	@Autowired
	private RatingService ratingService;
	
	public ModelMap createModel(Review review) {
		
		ModelMap modelMap = new ModelMap();
		
		Book book = review.getBook();
		
		if (contextService.isReaderAuthenticated()) {
			int loggedReaderId = contextService.getLoggedReaderId();
			Rating readerRating = ratingService.getReaderRatingObject(loggedReaderId, book.getId());
			
			if (readerRating != null)
				modelMap.addAttribute("loggedReaderRating", readerRating);
			else
				modelMap.addAttribute("loggedReaderRating", new Rating());
			
		}
		
		return modelMap;
	}
	
}
