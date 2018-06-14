package com.allbooks.webapp.service;

import com.allbooks.webapp.entity.Like;

public interface LikeService {

	Like getLikeByReviewIdAndReaderId(int reviewId,int readerId);

	void deleteLikeById(int likeId);
	
}
