package com.allbooks.webapp.utils.bookactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.service.RatingService;

@Component
public class PostReviewRatingGetter {

	@Autowired
	private RatingService ratingService;

	public Rating getRatingByIdOrCreateNew(int ratingId, int rate) {

		if (checkIfObjectAlreadyExists(ratingId))
			return getRatingAndSetNewRate(ratingId, rate);
		else
			return createNewRatingWithRate(rate);

	}

	private boolean checkIfObjectAlreadyExists(int objectId) {
		return objectId != 0;
	}

	private Rating getRatingAndSetNewRate(int ratingId, int rate) {
		Rating rating = ratingService.getRatingById(ratingId);
		rating.setRate(rate);
		return rating;
	}

	private Rating createNewRatingWithRate(int rate) {
		Rating rating = new Rating(rate);
		return rating;
	}

}
