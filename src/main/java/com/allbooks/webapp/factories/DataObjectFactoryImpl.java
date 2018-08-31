package com.allbooks.webapp.factories;

import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.RatingData;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.enumeration.ShelvesState;
import com.allbooks.webapp.enumeration.TokenType;
import com.allbooks.webapp.utils.entity.ReaderBookData;
import com.allbooks.webapp.utils.entity.ReviewData;
import com.allbooks.webapp.utils.entity.TokenData;

@Component
public class DataObjectFactoryImpl extends DataObjectFactory {

	@Override
	public ReaderBookData createReaderBookData(ShelvesState shelvesState, int bookId) {

		ReaderBookData readerBookData = new ReaderBookData(shelvesState, bookId);

		return readerBookData;
	}

	@Override
	public RatingData createRatingData(Rating rating, int bookId) {

		RatingData ratingData = new RatingData(rating, bookId);

		return ratingData;
	}

	@Override
	public ReviewData createReviewData(Review review, int bookId) {

		ReviewData reviewData = new ReviewData(review, bookId);

		return reviewData;
	}

	@Override
	public TokenData createTokenData(int readerId,String token,TokenType tokenType) {

		TokenData tokenData = new TokenData(readerId,token,tokenType);
		
		return tokenData;
	}

}
