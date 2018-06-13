package com.allbooks.webapp.utils.service;

import java.io.IOException;

import com.allbooks.webapp.entity.RatingData;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBookData;
import com.allbooks.webapp.entity.ReviewData;

public interface SaveService {

	public void saveRating(RatingData ratingData);
	public void saveReaderBook(ReaderBookData readerBookData) throws IOException;
	public void saveReview(ReviewData reviewData);
	public void saveReader(Reader reader);
	
}
