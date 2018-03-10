package com.allbooks.webapp.utils;

import com.allbooks.webapp.entity.Rating;

public class AverageRating {

	public static int getAverageRating(Rating[] ratings) {
		
		int sum = 0;
		int avg = 0;
		
		int ratingsLength = ratings.length;

		for (Rating rating : ratings) {
			sum += rating.getRate();
		}
		if (ratingsLength != 0)
			avg = sum / ratingsLength;
		else
			avg = 0;

		return avg;
		
	}
	
}
