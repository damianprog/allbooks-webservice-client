package com.allbooks.webapp.factories;

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

public abstract class BookActionDataObjectFactory {

	public abstract ReaderBookData createReaderBookData(ReaderBook readerBook,int bookId,boolean isItUpdateReaderBook);

	public abstract RatingData createRatingData(Rating rating,int bookId);
	
	public abstract ReviewData createReviewData(Review review,int bookId);
	
	public abstract CommentData createCommentData(Comment comment,int reviewId,int bookId);
	
	public abstract ReadingChallangeCommentData createReadingChallangeCommentData(ReadingChallangeComment challangeComment,int challangeReaderId);
	
}
