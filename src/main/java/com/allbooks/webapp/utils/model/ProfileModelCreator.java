package com.allbooks.webapp.utils.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.enumeration.ShelvesState;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.service.FriendsService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.service.PhotoService;
import com.allbooks.webapp.utils.service.ReaderBooksUtilsService;

@Component
public class ProfileModelCreator {

	@Autowired
	private ModelMapFactory modelMapFactory;

	@Autowired
	private ReaderService readerService;

	@Autowired
	private LoggedReaderModelProfileCreator loggedReaderModelProfileCreator;

	@Autowired
	private FriendsService friendsService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private ReaderBooksUtilsService readerBooksUtilsService;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private ReaderBookService readerBookService;

	private Reader reader;

	private ModelMap modelMap;

	public ModelMap create(int readerId) {

		initializeThisFields(readerId);

		initializeReaderBooksQuantitiesModel();

		initializeProfilePhotoModel();

		initializeFriendsBoxModel();

		initializeLatestReaderReviewsListModel();

		initializeCurrentlyReadingBooksModel();

		initializeReaderModel();

		initializeProfileModelForLoggedReader();

		return modelMap;
	}

	private void initializeThisFields(int readerId) {
		this.reader = readerService.getReaderById(readerId);
		this.modelMap = modelMapFactory.createInstance();
	}

	private void initializeReaderBooksQuantitiesModel() {

		Map<String, Integer> readerBooksQuantitiesMap = readerBooksUtilsService
				.getReaderBooksQuantities(reader.getId());

		modelMap.addAttribute("read", readerBooksQuantitiesMap.get("read"));
		modelMap.addAttribute("currentlyReading", readerBooksQuantitiesMap.get("currentlyReading"));
		modelMap.addAttribute("wantToRead", readerBooksQuantitiesMap.get("wantToRead"));

	}

	private void initializeProfilePhotoModel() {

		byte[] profilePhotoBytes = reader.getProfilePhoto();

		modelMap.addAttribute("profilePic", photoService.getEncodedImage(profilePhotoBytes));
	}

	private void initializeFriendsBoxModel() {

		List<Reader> friends = friendsService.getReaderFriends(reader.getId());

		modelMap.addAttribute("friendsNum", friends.size());
		modelMap.addAttribute("friends", friends);
	}

	private void initializeLatestReaderReviewsListModel() {

		List<Review> reviewsList = reviewService.getLatestReaderReviews(reader.getId());

		photoService.encodeAndResizeBookPhotoInBookChildren(reviewsList, 80, 87);

		modelMap.addAttribute("readerReviews", reviewsList);

	}

	private void initializeCurrentlyReadingBooksModel() {

		List<ReaderBook> currentlyReadingBooks = readerBookService.getReaderBooksByShelves(reader.getId(),
				ShelvesState.CURRENTLY_READING);

		photoService.encodeAndResizeBookPhotoInBookChildren(currentlyReadingBooks, 120, 200);

		modelMap.addAttribute("currentlyReadingList", currentlyReadingBooks);

	}

	private void initializeReaderModel() {

		modelMap.addAttribute("details", reader.getDetails());
		modelMap.addAttribute("reader", reader);
	}

	private void initializeProfileModelForLoggedReader() {
		modelMap.addAllAttributes(loggedReaderModelProfileCreator.createModel(reader));
	}

}
