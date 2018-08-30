package com.allbooks.webapp.utils.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.service.CommentService;
import com.allbooks.webapp.service.ReviewService;

@Component
public class ReviewPageModelCreator {

	@Autowired
	private ModelMapFactory modelMapFactory;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private LoggedReaderReviewPageModelCreator loggedReviewPageModelCreator;

	@Autowired
	private CommentService commentService;
	
	private ModelMap modelMap;

	private Review review;

	public ModelMap create(int reviewId) {

		initializeThisFields(reviewId);

		initializeReviewCommentsModel();

		initializeReviewFieldsModel();

		modelMap.addAllAttributes(loggedReviewPageModelCreator.createModel(review));

		return modelMap;
	}

	private void initializeReviewFieldsModel() {
		modelMap.addAttribute("book", review.getBook());
		modelMap.addAttribute("review", review);
		modelMap.addAttribute("rating", review.getRating());
	}

	private void initializeReviewCommentsModel() {
		modelMap.addAttribute("reviewComments", commentService.getReviewComments(review.getId()));
	}

	private void initializeThisFields(int reviewId) {
		this.modelMap = modelMapFactory.createInstance();
		this.review = reviewService.getReviewById(reviewId);
	}

}
