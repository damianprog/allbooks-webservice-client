package com.allbooks.webapp.utils.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.ReadingChallange;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.service.ReadingChallangeService;
import com.allbooks.webapp.utils.readerbook.CurrentYearReadBooksGetter;

@Component
public class MainPageModelCreator {

	@Autowired
	private SecurityContextService securityContextService;

	@Autowired
	private ReaderBookService readerBookService;

	@Autowired
	private RatingService ratingService;

	@Autowired
	private ReadingChallangeService readingChallangeService;

	@Autowired
	private CurrentYearReadBooksGetter currentYearReadBooksGetter;

	public ModelMap createModel() {

		ModelMap modelMap = new ModelMap();

		if (!securityContextService.isReaderAuthenticated()) {
			Reader reader = new Reader();
			modelMap.addAttribute("reader", reader);
			modelMap.addAttribute("isAuthenticated", false);

			return modelMap;
		} else {

			int readerId = securityContextService.getLoggedReaderId();

			ReadingChallange readingChallange = readingChallangeService.getReadingChallangeByReaderId(readerId);

			if (readingChallange != null) {

				List<ReaderBook> readBooks = currentYearReadBooksGetter.getBooks(readerId);

				int readingProgressPercentage = (readBooks.size() * 100) / readingChallange.getNumberOfBooks();

				modelMap.addAttribute("readingProgressPercentage", readingProgressPercentage);
				modelMap.addAttribute("currentNumberOfBooks", readBooks.size());
				modelMap.addAttribute("readingChallange", readingChallange);
			}

			modelMap.addAttribute("latestReaderbooks", readerBookService.get10LatestReaderBooks());
			modelMap.addAttribute("latestRatings", ratingService.get10LatestRatings());
			modelMap.addAttribute("isAuthenticated", true);
			modelMap.addAttribute("loggedReaderId", readerId);

			return modelMap;
		}

	}

}
