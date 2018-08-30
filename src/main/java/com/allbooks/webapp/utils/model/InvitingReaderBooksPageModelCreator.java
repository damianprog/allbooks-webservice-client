package com.allbooks.webapp.utils.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.service.PhotoServiceImpl;

@Component
public class InvitingReaderBooksPageModelCreator {

	@Autowired
	private ReaderService readerService;

	@Autowired
	private ReaderBookService readerBookService;

	@Autowired
	private PhotoServiceImpl photoService;

	@Autowired
	private RatingService ratingService;

	@Autowired
	private ModelMapFactory modelMapFactory;
	
	public ModelMap createModel(int invitingReaderId) {
		
		ModelMap modelMap = modelMapFactory.createInstance();
		
		modelMap.addAttribute("readerBooksList", prepareReaderBooksList(invitingReaderId));
		modelMap.addAttribute("invitingReader", readerService.getReaderById(invitingReaderId));
		modelMap.addAttribute("invitingReaderReaderBooksQuantity",
				readerBookService.getReaderBooks(invitingReaderId).size());
		
		return modelMap;
	}

	private List<ReaderBook> prepareReaderBooksList(int invitingReaderId) {
		List<ReaderBook> readerBooksList = readerBookService.get10LatestReaderBooksByReaderId(invitingReaderId);
		
		setOverallRatingInReaderBooks(readerBooksList);
		
		return readerBooksList;
	}

	private void setOverallRatingInReaderBooks(List<ReaderBook> readerBooksList) {
		for (ReaderBook tempReaderBook : readerBooksList) 
			tempReaderBook.setOverallRating(ratingService.getOverallRating(tempReaderBook.getBook().getId()));
	}
	
}
