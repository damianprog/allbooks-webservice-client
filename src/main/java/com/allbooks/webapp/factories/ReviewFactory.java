package com.allbooks.webapp.factories;

import java.util.Map;

import com.allbooks.webapp.entity.Review;

public abstract class ReviewFactory {

	public abstract Review createInstanceFromParams(Map<String,String> params);
	
}
