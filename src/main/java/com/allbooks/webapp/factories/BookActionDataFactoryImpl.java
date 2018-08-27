package com.allbooks.webapp.factories;

import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.CommentData;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.RatingData;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.ReaderBookData;
import com.allbooks.webapp.entity.ReadingChallangeComment;
import com.allbooks.webapp.entity.ReadingChallangeCommentData;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.entity.ReviewData;

@Component
public class BookActionDataFactoryImpl extends BookActionDataObjectFactory {

	@Override
	public ReaderBookData createReaderBookData(ReaderBook readerBook,int bookId, boolean isItUpdateReaderBook) {

		ReaderBookData readerBookData = new ReaderBookData(readerBook, bookId, isItUpdateReaderBook);

		return readerBookData;
	}

	@Override
	public RatingData createRatingData(Rating rating, int bookId) {

		RatingData ratingData = new RatingData(rating, bookId);

		return ratingData;
	}

	@Override
	public ReviewData createReviewData(Review review,int bookId) {

		ReviewData reviewData = new ReviewData(review, bookId);

		return reviewData;
	}

	@Override
	public CommentData createCommentData(Comment comment, int bookId, int reviewId) {
		
		return new CommentData(comment,bookId,reviewId);
		
		
	}

	@Override
	public ReadingChallangeCommentData createReadingChallangeCommentData(ReadingChallangeComment challangeComment,int challangeReaderId) {
		return new ReadingChallangeCommentData(challangeComment,challangeReaderId);
	}
}
