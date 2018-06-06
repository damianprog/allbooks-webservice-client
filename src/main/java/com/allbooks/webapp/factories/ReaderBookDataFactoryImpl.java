package com.allbooks.webapp.factories;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.RatingData;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.ReaderBookData;

@Component
public class ReaderBookDataFactoryImpl extends BookActionDataObjectFactory {

	@Override
	public ReaderBookData createReaderBookData(ReaderBook readerBook, Map<String, String> params) {

		ReaderBookData readerBookData = new ReaderBookData(readerBook, Integer.parseInt(params.get("bookId")),
				Boolean.valueOf(params.get("isItUpdateReaderBook")));

		return readerBookData;
	}

	@Override
	public RatingData createRatingData(Rating rating, Map<String, String> params) {
		RatingData ratingData = new RatingData(rating, Integer.parseInt(params.get("bookId")),
				Boolean.valueOf(params.get("isItUpdateReaderBook")));
		
		return ratingData;
	}

}
