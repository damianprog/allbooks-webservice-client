package com.allbooks.webapp.utils.service;

import java.io.IOException;

import com.allbooks.webapp.entity.RatingData;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBookData;
import com.allbooks.webapp.entity.ReviewData;

public interface SaveService {

	void saveRating(RatingData ratingData);

	void saveReaderBook(ReaderBookData readerBookData) throws IOException;

	void saveReview(ReviewData reviewData);

	Reader saveReader(Reader reader);

}
