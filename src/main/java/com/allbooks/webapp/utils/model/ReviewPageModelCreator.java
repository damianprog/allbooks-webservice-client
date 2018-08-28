package com.allbooks.webapp.utils.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.ReaderPost;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.photos.ReaderPostsWithPreparedReadersPhotoGetter;
import com.allbooks.webapp.utils.service.PhotoService;

@Component
public class ReviewPageModelCreator {

	@Autowired
	private ModelMapFactory modelMapFactory;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private ReaderPostsWithPreparedReadersPhotoGetter reviewReaderPhotoPreparer;

	@Autowired
	private LoggedReaderReviewPageModelCreator loggedReviewPageModelCreator;

	@Autowired
	private PhotoService photoService;

	private ModelMap modelMap;

	private Review review;

	private Book book;

	public ModelMap create(int reviewId) {

		initializeThisFields(reviewId);

		initializeReviewCommentsModel();

		initializeReviewFieldsModel();

		modelMap.addAllAttributes(loggedReviewPageModelCreator.createModel(review));

		modelMap.addAttribute("bookPhoto", photoService.getEncodedImage(book.getBookPhoto()));

		return modelMap;
	}

	private void initializeReviewFieldsModel() {
		modelMap.addAttribute("book", review.getBook());
		modelMap.addAttribute("review", review);
		modelMap.addAttribute("rating", review.getRating());
	}

	private void initializeReviewCommentsModel() {
		List<ReaderPost> reviewComments = reviewReaderPhotoPreparer.getPreparedReviewComments(review.getId());
		modelMap.addAttribute("reviewComments", reviewComments);
	}

	private void initializeThisFields(int reviewId) {
		this.modelMap = modelMapFactory.createInstance();
		this.review = reviewService.getReviewById(reviewId);
		this.book = review.getBook();
	}

}
