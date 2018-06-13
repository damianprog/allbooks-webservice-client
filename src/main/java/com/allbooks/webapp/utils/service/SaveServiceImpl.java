package com.allbooks.webapp.utils.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.RatingData;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBookData;
import com.allbooks.webapp.entity.ReviewData;
import com.allbooks.webapp.utils.RatingSaver;
import com.allbooks.webapp.utils.ReaderBookSaver;
import com.allbooks.webapp.utils.ReaderSaver;
import com.allbooks.webapp.utils.ReviewSaver;

@Service
public class SaveServiceImpl implements SaveService {

	@Autowired
	private RatingSaver ratingSaver;

	@Autowired
	private ReaderBookSaver readerBookSaver;

	@Autowired
	private ReviewSaver reviewSaver;

	@Autowired
	private ReaderSaver readerSaver;
	
	@Override
	public void saveRating(RatingData ratingData) {

		ratingSaver.save(ratingData);
	}

	@Override
	public void saveReaderBook(ReaderBookData readerBookData) throws IOException {
		readerBookSaver.save(readerBookData);
	}

	@Override
	public void saveReview(ReviewData reviewData) {

		reviewSaver.save(reviewData);
	}

	@Override
	public void saveReader(Reader reader) {
		
		readerSaver.save(reader);

	}

}
