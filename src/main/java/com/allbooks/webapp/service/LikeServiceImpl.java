package com.allbooks.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Like;
import com.allbooks.webapp.webservice.LikeWebservice;

@Service
public class LikeServiceImpl implements LikeService{

	@Autowired
	private LikeWebservice likeWebservice;

	@Override
	public Like getLikeByReviewIdAndReaderId(int reviewId, int readerId) {
		
		return likeWebservice.getLikeByReviewIdAndReaderId(reviewId, readerId);
	}

	@Override
	public void deleteLikeById(int likeId) {
		
		likeWebservice.deleteLikeById(likeId);
	}
	
	
	
}
