package com.allbooks.webapp.utils;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Like;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.factories.LikeFactory;
import com.allbooks.webapp.service.LikeService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReviewService;

@Component
public class LikesDropper {

	@Autowired
	private HttpSession session;

	@Autowired
	private LikeService likeService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private ReaderService readerService;

	@Autowired
	private LikeFactory likeFactory;

	public void dropLike(int reviewId) {

		int loggedReaderId = (int) session.getAttribute("readerId");

		Reader loggedReader = readerService.getReaderById(loggedReaderId);

		Like like = likeService.getLikeByReviewIdAndReaderId(reviewId, loggedReaderId);

		if (like != null)
			likeService.deleteLikeById(like.getId());
		else {
			Review review = reviewService.getReviewById(reviewId);

			List<Like> reviewLikes = review.getLikes();

			Like newLike = likeFactory.createInstance(loggedReader);

			reviewLikes.add(newLike);

			reviewService.updateReview(review);

		}

	}

}
