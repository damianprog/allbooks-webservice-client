package com.allbooks.webapp.utils.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.RatingData;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.utils.entity.CommentData;
import com.allbooks.webapp.utils.entity.ReaderBookData;
import com.allbooks.webapp.utils.entity.ReadingChallangeCommentData;
import com.allbooks.webapp.utils.entity.ReviewData;
import com.allbooks.webapp.utils.saver.CommentSaver;
import com.allbooks.webapp.utils.saver.FavouriteGenresSaver;
import com.allbooks.webapp.utils.saver.RatingSaver;
import com.allbooks.webapp.utils.saver.ReaderBookSaver;
import com.allbooks.webapp.utils.saver.ReaderSaver;
import com.allbooks.webapp.utils.saver.ReadingChallangeCommentSaver;
import com.allbooks.webapp.utils.saver.ReadingChallangeSaver;
import com.allbooks.webapp.utils.saver.ReviewSaver;

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
	
	@Autowired
	private ReadingChallangeSaver readingChallangeSaver;
	
	@Autowired
	private FavouriteGenresSaver favouriteGenresSaver;
	
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
	public void saveNewComment(CommentData commentData) {
		commentSaver.save(commentData);
	}

	@Override
	public void saveReadingChallangeComment(ReadingChallangeCommentData readingChallangeCommentData) {
		readingChallangeCommentSaver.save(readingChallangeCommentData);
	}

	@Override
	public void saveFavouriteGenresByCheckedGenresList(List<String> categoriesChecked) {
		favouriteGenresSaver.save(categoriesChecked);
	}

	@Override
	public void saveReadingChallangeByNumberOfBooks(int numberOfBooks) {
		readingChallangeSaver.save(numberOfBooks);
	}

	
	
}
