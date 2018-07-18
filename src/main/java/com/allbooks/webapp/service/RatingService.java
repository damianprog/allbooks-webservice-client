package com.allbooks.webapp.service;

import java.util.List;

import com.allbooks.webapp.entity.Rating;

public interface RatingService {

	Rating getReaderRatingObject(int readerId, int bookId);

	Rating submitRating(Rating rating);

	Rating[] getBookRatings(int bookId);

	double getOverallRating(int bookId);

	int getReaderRating(String username, String bookName);

	Rating getLoggedReaderRatingObject(int bookId);

	Rating getRatingById(int ratingId);
	
	List<Rating> get10LatestRatings();

}
