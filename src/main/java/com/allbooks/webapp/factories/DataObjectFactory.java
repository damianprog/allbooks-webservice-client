package com.allbooks.webapp.factories;

import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.RatingData;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.enumeration.ShelvesState;
import com.allbooks.webapp.enumeration.TokenType;
import com.allbooks.webapp.utils.entity.ReaderBookData;
import com.allbooks.webapp.utils.entity.ReviewData;
import com.allbooks.webapp.utils.entity.TokenData;

public abstract class DataObjectFactory {

	public abstract ReaderBookData createReaderBookData(ShelvesState shelvesState,int bookId);

	public abstract RatingData createRatingData(Rating rating,int bookId);
	
	public abstract ReviewData createReviewData(Review review,int bookId);
	
	public abstract TokenData createTokenData(int readerId,String token,TokenType tokenType);
	
}
