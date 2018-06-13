package com.allbooks.webapp.utils;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderBookService;

@Component
public class ReaderBookAndRatingModelCreator {

	@Autowired
	private HttpSession session;
	
	@Autowired
	private SecurityContextService securityContextService;
	
	@Autowired
	private ModelMapFactory modelMapFactory;
	
	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private ReaderBookService readerBookService;
	
	public ModelMap createModel(int bookId) {
		
		ModelMap modelMap = modelMapFactory.createInstance();
		
		if (securityContextService.isReaderAuthenticated()) {

			int readerId = (int) session.getAttribute("readerId");

			Rating rating = ratingService.getLoggedReaderRatingObject(bookId);
			
			if(rating == null)
				rating = new Rating();
			
			ReaderBook readerBook = readerBookService.getReaderBook(bookId, readerId);
			
			if(readerBook == null)
				readerBook = new ReaderBook();
			
			modelMap.addAttribute("readerBook",readerBook);
			modelMap.addAttribute("rating", rating);
		}
		
		return modelMap;
	}
	
}
