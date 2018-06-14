package com.allbooks.webapp.webservice;

import com.allbooks.webapp.entity.Like;

public interface LikeWebservice {
	
	Like getLikeByReviewIdAndReaderId(int reviewId,int readerId);

	void deleteLikeById(int likeId);
	
}
