package com.allbooks.webapp.utils.bookactions;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.CommentData;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.CommentService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReviewService;

@Component
public class CommentSaver {

	@Autowired
	private ReaderService readerService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private RatingService ratingService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private BookService bookService;
	
	@Autowired
	private SecurityContextService contextService;
	
	public void save(CommentData commentData) {

		int readerId = contextService.getLoggedReaderId();
		
		Comment comment = initializeCommentFields(commentData, readerId);

		commentService.submitComment(comment);
	}

	private Comment initializeCommentFields(CommentData commentData, int readerId) {
		
		int bookId = commentData.getBookId();
		
		Comment comment = commentData.getComment();
		
		Reader reader = readerService.getReaderById(readerId);
		
		Book book = bookService.getBookById(bookId);
		
		Rating rating = ratingService.getReaderRatingObject(readerId, bookId);

		Review review = reviewService.getReviewById(commentData.getReviewId());

		comment.setPostingReader(reader);
		comment.setRating(rating);
		comment.setReview(review);
		comment.setBook(book);
		return comment;
	}

}
