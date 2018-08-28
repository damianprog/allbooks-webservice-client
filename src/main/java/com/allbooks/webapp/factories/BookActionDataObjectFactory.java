package com.allbooks.webapp.factories;

import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.RatingData;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.utils.entity.ReaderBookData;
import com.allbooks.webapp.utils.entity.ReviewData;

public abstract class BookActionDataObjectFactory {

	public abstract ReaderBookData createReaderBookData(ReaderBook readerBook,int bookId,boolean isItUpdateReaderBook);

	public abstract RatingData createRatingData(Rating rating,int bookId);
	
	public abstract ReviewData createReviewData(Review review,int bookId);
	
}
