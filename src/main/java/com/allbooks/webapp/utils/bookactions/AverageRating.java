package com.allbooks.webapp.utils.bookactions;

import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Rating;

@Component
public class AverageRating {

	public int getAverageRating(Rating[] ratings) {
		
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
