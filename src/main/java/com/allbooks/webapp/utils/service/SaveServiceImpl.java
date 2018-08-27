package com.allbooks.webapp.utils.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.CommentData;
import com.allbooks.webapp.entity.RatingData;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBookData;
import com.allbooks.webapp.entity.ReadingChallangeCommentData;
import com.allbooks.webapp.entity.ReviewData;
import com.allbooks.webapp.utils.bookactions.RatingSaver;
import com.allbooks.webapp.utils.bookactions.ReviewSaver;
import com.allbooks.webapp.utils.ReadingChallangeCommentSaver;
import com.allbooks.webapp.utils.bookactions.CommentSaver;
import com.allbooks.webapp.utils.reader.ReaderSaver;
import com.allbooks.webapp.utils.readerbook.ReaderBookSaver;

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
	
	@Autowired
	private CommentSaver commentSaver;
	
	@Autowired
	private ReadingChallangeCommentSaver readingChallangeCommentSaver;
	
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
	public Reader saveReader(Reader reader) {
		
		return readerSaver.save(reader);

	}

	@Override
	public void saveComment(CommentData commentData) {
		commentSaver.save(commentData);
	}

	@Override
	public void saveReadingChallangeComment(ReadingChallangeCommentData readingChallangeCommentData) {
		readingChallangeCommentSaver.save(readingChallangeCommentData);
	}

	
	
}
