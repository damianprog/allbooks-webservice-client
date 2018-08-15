package com.allbooks.webapp.utils.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.ReadingChallange;
import com.allbooks.webapp.enumeration.ShelvesStates;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.service.ReadingChallangeService;
import com.allbooks.webapp.utils.RecommendationGetter;
import com.allbooks.webapp.utils.readerbook.CurrentYearReadBooksGetter;
import com.allbooks.webapp.utils.service.PhotoService;
import com.allbooks.webapp.utils.service.ReaderBooksUtilsService;

@Component
public class MainPageModelCreator {

	@Autowired
	private SecurityContextService securityContextService;

	@Autowired
	private ReaderBookService readerBookService;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private ReadingChallangeService readingChallangeService;

	@Autowired
	private CurrentYearReadBooksGetter currentYearReadBooksGetter;

	@Autowired
	private ReaderBooksUtilsService readerBooksUtilsService;
	
	@Autowired
	private RecommendationGetter recommendationGetter;
	
	@Autowired
	private RatingService ratingService;
	
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

			List<ReaderBook> latestReaderBooks = readerBookService.get10LatestReaderBooks();

			photoService.encodeAndResizeBookPhotoInBookChildren(latestReaderBooks, 135, 210);

			List<ReaderBook> wantToReadBooks = readerBookService.getReaderBooksByShelves(readerId,
					ShelvesStates.WANT_TO_READ);
			
			photoService.encodeAndResizeBookPhotoInBookChildren(wantToReadBooks, 96, 120);
			
			Map<String, Integer> readerBooksQuantitiesMap = readerBooksUtilsService.getReaderBooksQuantities(readerId);
			
			Book recommendedBook = recommendationGetter.getRecommendedBook();
			
			modelMap.addAttribute("recommendedBook", recommendedBook);
			modelMap.addAttribute("recommendedBookRating", ratingService.getOverallRating(recommendedBook.getId()));
			
			modelMap.addAttribute("read", readerBooksQuantitiesMap.get("read"));
			modelMap.addAttribute("currentlyReading", readerBooksQuantitiesMap.get("currentlyReading"));
			modelMap.addAttribute("wantToRead", readerBooksQuantitiesMap.get("wantToRead"));
			modelMap.addAttribute("wantToReadBooks",wantToReadBooks);
			modelMap.addAttribute("latestReaderbooks", latestReaderBooks);
			modelMap.addAttribute("isAuthenticated", true);
			modelMap.addAttribute("loggedReaderId", readerId);

			return modelMap;
		}

	}

}
