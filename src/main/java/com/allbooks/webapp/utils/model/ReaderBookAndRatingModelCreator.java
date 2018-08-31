package com.allbooks.webapp.utils.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderBookService;

@Component
public class ReaderBookAndRatingModelCreator {

	@Autowired
	private SecurityContextService contextService;

	@Autowired
	private ModelMapFactory modelMapFactory;

	@Autowired
	private RatingService ratingService;

	@Autowired
	private ReaderBookService readerBookService;

	private int bookId;

	private int readerId;

	private ModelMap modelMap;

	public ModelMap createModel(int bookId) {

		initializeModelMapIfReaderIsAuthenticated();

		return modelMap;
	}

	private void initializeModelMapIfReaderIsAuthenticated() {
		
		if (contextService.isReaderAuthenticated())
			initializeAuthenticatedReaderModelMap();
	}

	private void initializeAuthenticatedReaderModelMap() {

		initializeThisFields(bookId);

		initializeRatingAttribute();

		initializeReaderBookAttribute();

	}

	private void initializeThisFields(int bookId) {
		this.readerId = contextService.getLoggedReaderId();
		this.bookId = bookId;
		this.modelMap = modelMapFactory.createInstance();
	}

	private void initializeRatingAttribute() {
		Rating rating = ratingService.getLoggedReaderRatingObject(bookId);

		if (rating == null)
			rating = new Rating();

		modelMap.addAttribute("rating", rating);
	}

	private void initializeReaderBookAttribute() {
		ReaderBook readerBook = readerBookService.getReaderBook(bookId, readerId);

		if (readerBook == null)
			readerBook = new ReaderBook();

		modelMap.addAttribute("readerBook", readerBook);

	}

}
