package com.allbooks.webapp.factories;

import java.util.Map;

import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.CommentData;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.RatingData;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.ReaderBookData;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.entity.ReviewData;

public abstract class BookActionDataObjectFactory {

	public abstract ReaderBookData createReaderBookData(ReaderBook readerBook,Map<String,String> params);

	public abstract RatingData createRatingData(Rating rating,Map<String,String> params);
	
	public abstract ReviewData createReviewData(Review review,Map<String,String> params);
	
	public abstract CommentData createCommentData(Comment comment,Map<String,String> params);
	
}
