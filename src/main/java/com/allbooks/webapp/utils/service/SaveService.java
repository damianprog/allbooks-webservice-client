package com.allbooks.webapp.utils.service;

import java.io.IOException;
import java.util.Map;

import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.Review;

public interface SaveService {

	public void saveRating(Rating rating,Map<String,String> params,String username);
	public void saveReaderBook(ReaderBook readerBook,Map<String,String> params , String username) throws IOException;
	public void saveReview(Map<String,String> params,String username);
	public boolean saveReader(Reader reader);
	
}
