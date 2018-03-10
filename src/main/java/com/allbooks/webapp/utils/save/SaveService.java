package com.allbooks.webapp.utils.save;

import java.io.IOException;

import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.Review;

public interface SaveService {

	public void saveRating(Rating rating,boolean update,String bookName,String username);
	public void saveReaderBook(String bookName, boolean update, ReaderBook readerBook, String username) throws IOException;
	public void saveReview(Review review,String username,String bookName);
}
