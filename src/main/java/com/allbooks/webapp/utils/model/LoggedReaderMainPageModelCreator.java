package com.allbooks.webapp.utils.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.enumeration.ShelvesState;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.utils.ReadingChallangeBoxCreator;
import com.allbooks.webapp.utils.RecommendationGetter;
import com.allbooks.webapp.utils.service.ReaderBooksUtilsService;

@Component
public class LoggedReaderMainPageModelCreator {

	@Autowired
	private SecurityContextService contextService;

	@Autowired
	private ModelMapFactory modelMapFactory;

	@Autowired
	private ReaderBookService readerBookService;

	@Autowired
	private ReadingChallangeBoxCreator readingChallangeBoxCreator;

	@Autowired
	private ReaderBooksUtilsService readerBooksUtilsService;

	@Autowired
	private RecommendationGetter recommendationGetter;

	@Autowired
	private RatingService ratingService;

	private ModelMap modelMap;

	private int readerId;

	public ModelMap createModel() {

		initializeThisFields();

		initializeReadingChallangeBoxModel();

		intializeLatestReaderBooksAttribute();

		intializeWantToReadBooksAttribute();

		initializeReaderBooksQuantities();

		initializeRecommendedBookAttibute();

		return modelMap;
	}

	private void initializeThisFields() {
		this.modelMap = modelMapFactory.createInstance();
		this.readerId = contextService.getLoggedReaderId();
	}

	private void initializeReadingChallangeBoxModel() {
		modelMap.addAllAttributes(readingChallangeBoxCreator.create(readerId));
	}

	private void intializeLatestReaderBooksAttribute() {
		List<ReaderBook> latestReaderBooks = readerBookService.get10LatestReaderBooks();
		modelMap.addAttribute("latestReaderbooks", latestReaderBooks);
	}

	private void intializeWantToReadBooksAttribute() {
		List<ReaderBook> wantToReadBooks = readerBookService.getReaderBooksByShelves(readerId,
				ShelvesState.WANT_TO_READ);
		modelMap.addAttribute("wantToReadBooks", wantToReadBooks);
	}

	private void initializeReaderBooksQuantities() {
		Map<String, Integer> readerBooksQuantitiesMap = readerBooksUtilsService.getReaderBooksQuantities(readerId);

		modelMap.addAttribute("read", readerBooksQuantitiesMap.get("read"));
		modelMap.addAttribute("currentlyReading", readerBooksQuantitiesMap.get("currentlyReading"));
		modelMap.addAttribute("wantToRead", readerBooksQuantitiesMap.get("wantToRead"));
	}

	private void initializeRecommendedBookAttibute() {
		Book recommendedBook = recommendationGetter.getRecommendedBook();

		if (recommendedBook != null) {
			modelMap.addAttribute("recommendedBook", recommendedBook);
			modelMap.addAttribute("recommendedBookRating", ratingService.getOverallRating(recommendedBook.getId()));
		}
	}

}
