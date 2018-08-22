package com.allbooks.webapp.utils.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.Notification;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReadingChallangeComment;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.service.CommentService;
import com.allbooks.webapp.service.NotificationService;
import com.allbooks.webapp.service.ReadingChallangeCommentService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.entity.CommentRemovalData;

@Service
public class PostsRemover {

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private ReadingChallangeCommentService challangeCommentService;

	public void deleteReview(int reviewId, String reasonText) {

		Review review = reviewService.getReviewById(reviewId);

		Reader reader = review.getPostingReader();

		Book book = review.getBook();

		String reviewDeleteInfo = "Your review: '" + review.getTitle() + "' of book: '" + book.getMiniTitle()
				+ "' has been removed.Reason: " + reasonText;

		saveNotification(reader, reviewDeleteInfo);

		reviewService.deleteReviewById(reviewId);
	}

	private void deleteComment(CommentRemovalData commentRemovalData) {
		Comment comment = commentService.getCommentById(commentRemovalData.getCommentId());

		Reader reader = comment.getPostingReader();

		Book book = comment.getBook();

		String commentDeleteInfo = "Your comment to review: '" + comment.getReview().getTitle() + "' of book: '"
				+ book.getMiniTitle() + "' has been removed.Reason: " + commentRemovalData.getReasonOfRemoval();

		saveNotification(reader, commentDeleteInfo);

		commentService.deleteCommentById(comment.getId());
	}

	private void deleteReadingChallangeComment(CommentRemovalData commentRemovalData) {

		ReadingChallangeComment challangeComment = challangeCommentService.getCommentById(commentRemovalData.getCommentId());

		Reader postingReader = challangeComment.getPostingReader();

		Reader challangeReader = challangeComment.getChallangeReader();

		String commentDeleteInfo = "Your comment to Reading challange of reader: " + challangeReader.getUsername()
				+ " has been removed.Reason: " + commentRemovalData.getReasonOfRemoval();

		saveNotification(postingReader, commentDeleteInfo);

		challangeCommentService.deleteCommentById(challangeComment.getId());

	}

	private void saveNotification(Reader reader, String deleteInfo) {

		Notification notification = new Notification(reader, deleteInfo);

		notificationService.saveNotification(notification);
	}

	public void deleteCommentByTypeAndId(CommentRemovalData commentRemovalData) {

		switch (commentRemovalData.getCommentType()) {
			case REVIEW_COMMENT:
					deleteComment(commentRemovalData);
					break;
			case CHALLANGE_COMMENT:
					deleteReadingChallangeComment(commentRemovalData);
					break;
		}

	}

}
