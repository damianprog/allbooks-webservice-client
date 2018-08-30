package com.allbooks.webapp.utils.saver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.factories.CommentFactory;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.CommentService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.entity.CommentData;

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

	@Autowired
	private CommentFactory commentFactory;
	
	private int bookId;

	private int reviewId;

	private String commentText;

	private int loggedReaderId;
	
	public void save(CommentData commentData) {

		initializeThisFields(commentData);

		Comment comment = createCommentAndInitializeCommentFields(commentData);

		commentService.submitComment(comment);
	}

	private void initializeThisFields(CommentData commentData) {

		this.loggedReaderId = contextService.getLoggedReaderId();
		this.bookId = commentData.getBookId();
		this.reviewId = commentData.getReviewId();
		this.commentText = commentData.getCommentText();
	}

	private Comment createCommentAndInitializeCommentFields(CommentData commentData) {

		Comment comment = commentFactory.createReviewCommentInstance();

		Reader reader = readerService.getReaderById(loggedReaderId);

		Book book = bookService.getBookById(bookId);

		Rating rating = ratingService.getReaderRatingObject(loggedReaderId, bookId);

		Review review = reviewService.getReviewById(reviewId);

		comment.setText(commentText);
		comment.setPostingReader(reader);
		comment.setRating(rating);
		comment.setReview(review);
		comment.setBook(book);
		return comment;
	}

}
