package com.allbooks.webapp.utils.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.enumeration.ShelvesStates;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.utils.ReadingChallangeBoxCreator;
import com.allbooks.webapp.utils.RecommendationGetter;
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
	private ReadingChallangeBoxCreator readingChallangeBoxCreator;

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

			modelMap.addAllAttributes(readingChallangeBoxCreator.create());

			List<ReaderBook> latestReaderBooks = readerBookService.get10LatestReaderBooks();

			photoService.encodeAndResizeBookPhotoInBookChildren(latestReaderBooks, 135, 210);

			List<ReaderBook> wantToReadBooks = readerBookService.getReaderBooksByShelves(readerId,
					ShelvesStates.WANT_TO_READ);
			
			photoService.encodeAndResizeBookPhotoInBookChildren(wantToReadBooks, 96, 120);
			
			Map<String, Integer> readerBooksQuantitiesMap = readerBooksUtilsService.getReaderBooksQuantities(readerId);
			
			Book recommendedBook = recommendationGetter.getRecommendedBook();
			
			if(recommendedBook != null) {
				modelMap.addAttribute("recommendedBook", recommendedBook);
				modelMap.addAttribute("recommendedBookRating", ratingService.getOverallRating(recommendedBook.getId()));
			}
			
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
