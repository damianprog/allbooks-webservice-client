package com.allbooks.webapp.utils.service;

import java.util.List;

import com.allbooks.webapp.entity.RatingData;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.utils.entity.CommentData;
import com.allbooks.webapp.utils.entity.ReaderBookData;
import com.allbooks.webapp.utils.entity.ReadingChallangeCommentData;
import com.allbooks.webapp.utils.entity.ReviewData;

public interface SaveService {

	void saveRating(RatingData ratingData);

	void saveReaderBook(ReaderBookData readerBookData);

	void saveReview(ReviewData reviewData);

	void saveNewComment(CommentData commentData);

	void saveReadingChallangeComment(ReadingChallangeCommentData readingChallangeCommentData);
	
	void saveFavouriteGenresByCheckedGenresList(List<String> categoriesChecked);
	
	void saveReadingChallangeByNumberOfBooks(int numberOfBooks);
	
	Reader saveReader(Reader reader);

}
