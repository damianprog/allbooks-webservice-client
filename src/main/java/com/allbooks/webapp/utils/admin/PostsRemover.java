package com.allbooks.webapp.utils.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.Notification;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.service.CommentService;
import com.allbooks.webapp.service.NotificationService;
import com.allbooks.webapp.service.ReviewService;

@Service
public class PostsRemover {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private NotificationService notificationService;
	
	public void deleteReview(int reviewId,String reasonText) {
		
		Review review = reviewService.getReviewById(reviewId);

		Reader reader = review.getReader();

		Book book = review.getBook();

		String reviewDeleteInfo = "Your review: '" + review.getTitle() + "' of book: '" + book.getMiniTitle()
				+ "' has been removed.Reason: " + reasonText;
		
		saveNotification(reader, reviewDeleteInfo);
		
		reviewService.deleteReviewById(reviewId);
	}
	
	public void deleteComment(int commentId,String reasonText) {
		Comment comment = commentService.getCommentById(commentId);

		Reader reader = comment.getReader();

		Book book = comment.getBook();

		String commentDeleteInfo = "Your comment to review: '" + comment.getReview().getTitle() + "' of book: '"
				+ book.getMiniTitle() + "' has been removed.Reason: " + reasonText;
		
		saveNotification(reader, commentDeleteInfo);
		
		commentService.deleteCommentById(commentId);
	}
	
	private void saveNotification(Reader reader,String deleteInfo) {
		
		Notification notification = new Notification(reader, deleteInfo);

		notificationService.saveNotification(notification);
	}
	
}
