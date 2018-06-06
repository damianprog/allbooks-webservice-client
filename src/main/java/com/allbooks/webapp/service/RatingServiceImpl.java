package com.allbooks.webapp.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.utils.AverageRating;
import com.allbooks.webapp.utils.webservice.UtilsWebservice;
import com.allbooks.webapp.webservice.RatingWebservice;

@Service
public class RatingServiceImpl implements RatingService{

	@Autowired
	private ReaderService readerService;
	
	@Autowired
	private RatingWebservice ratingWebservice;
	
	@Autowired
	private UtilsWebservice utilsWebservice;
	
	@Autowired
	private AverageRating averageRating;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public Rating getLoggedReaderRatingObject(int bookId) {
		
		int readerId = (int) session.getAttribute("readerId");
		
		Rating rating = ratingWebservice.getReaderRatingObject(bookId,readerId);

		if (rating == null)
			rating = new Rating();

		return rating;
	}
	
	@Override
	public Rating getReaderRatingObject(int readerId,int bookId) {

		Reader reader = readerService.getReaderById(readerId);

		Rating rating = ratingWebservice.getReaderRatingObject(bookId, reader.getId());

		if (rating == null)
			rating = new Rating();

		return rating;
	}

	@Override
	public void submitRating(Rating rating) {

		ratingWebservice.submitRating(rating);

	}

	@Override
	public double getOverallRating(int bookId) {

		Rating[] ratings = ratingWebservice.getBookRatings(bookId);

		return averageRating.getAverageRating(ratings);
	}

	@Override
	public int getReaderRating(String username, String bookName) {

		Reader reader = readerService.getReaderByUsername(username);

		Rating rating = ratingWebservice.getReaderRatingObject(utilsWebservice.getBookId(bookName), reader.getId());

		if (rating == null)
			return 0;

		return rating.getRate();
	}

	@Override
	public Rating[] getBookRatings(int bookId) {
		return ratingWebservice.getBookRatings(bookId);
	}
	
}
