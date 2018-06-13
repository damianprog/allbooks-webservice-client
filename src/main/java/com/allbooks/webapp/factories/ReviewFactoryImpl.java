package com.allbooks.webapp.factories;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Review;

@Component
public class ReviewFactoryImpl extends ReviewFactory{

	@Override
	public Review createInstanceFromParams(Map<String, String> params) {

		Review review = new Review();
		review.setTitle(params.get("title"));
		review.setText(params.get("text"));
		
		return review;
	}

}
