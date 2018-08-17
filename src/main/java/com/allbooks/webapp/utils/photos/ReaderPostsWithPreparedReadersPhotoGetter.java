package com.allbooks.webapp.utils.photos;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.ReaderPost;
import com.allbooks.webapp.service.CommentService;
import com.allbooks.webapp.service.ReadingChallangeCommentService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.service.PhotoService;

@Component
public class ReaderPostsWithPreparedReadersPhotoGetter {

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private PhotoService photoService;

	@Autowired
	private ReadingChallangeCommentService challangeCommentService;
	
	public List<ReaderPost> getPreparedBookReviews(int bookId) {

		List<ReaderPost> reviews = reviewService.getBookReviews(bookId);

		Iterator<ReaderPost> iter = reviews.iterator();
		
		setEncodedPhoto(iter, 80, 80);
		
		return reviews;
	}
	
	public List<ReaderPost> getPreparedReviewComments(int reviewId){
		
		List<ReaderPost> comments = commentService.getReviewComments(reviewId);
		
		Iterator<ReaderPost> iter = comments.iterator();
		
		setEncodedPhoto(iter, 80, 80);
		
		return comments;
	}

	public List<ReaderPost> getPreparedReadingChallangeComments(int challangeReaderId){
		
		List<ReaderPost> challangeComments = challangeCommentService.getReadingChallangeCommentsByChallangeReaderId(challangeReaderId);
		
		Iterator<ReaderPost> iter = challangeComments.iterator();
		
		setEncodedPhoto(iter, 80, 80);
		
		return challangeComments;
		
	}
	
	private void setEncodedPhoto(Iterator<ReaderPost> iter,int width,int height) {
		
		iter.forEachRemaining(e -> {
			e.getPostingReader()
			.setEncodedProfilePhoto(photoService.getEncodedImage(
					photoService.resize(e.getPostingReader().getProfilePhoto(),width,height)));
		});
		
	}
	
}
