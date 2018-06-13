package com.allbooks.webapp.webservice;

import com.allbooks.webapp.entity.Rating;

public interface RatingWebservice {

	public Rating getReaderRatingObject(int bookId, int readerId);

	public Rating submitRating(Rating rating);

	public Rating[] getBookRatings(int bookId);

	public int getReaderRating(String username, String bookName);
	
}
