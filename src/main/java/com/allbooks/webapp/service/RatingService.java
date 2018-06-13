package com.allbooks.webapp.service;

import com.allbooks.webapp.entity.Rating;

public interface RatingService {

	public Rating getReaderRatingObject(int readerId,int bookId);

	public Rating submitRating(Rating rating);

	public Rating[] getBookRatings(int bookId);
	
	public double getOverallRating(int bookId);

	public int getReaderRating(String username, String bookName);

	public Rating getLoggedReaderRatingObject(int bookId);
	
}
