package com.allbooks.webapp.utils.service;

import java.io.IOException;
import java.util.Map;

import com.allbooks.webapp.entity.RatingData;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBookData;

public interface SaveService {

	public void saveRating(RatingData ratingData);
	public void saveReaderBook(ReaderBookData readerBookData) throws IOException;
	public void saveReview(Map<String,String> params,String username);
	public boolean saveReader(Reader reader);
	
}
