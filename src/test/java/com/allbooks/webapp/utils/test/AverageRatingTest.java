package com.allbooks.webapp.utils.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.utils.AverageRating;

public class AverageRatingTest {

	@Test
	public void test() {
		
		Rating[] ratings = new Rating[5];
		
		for(int i=0;i<5;i++) {
			
			ratings[i] = new Rating();
			ratings[i].setRate(i+1);
			
		}
		
		assertEquals(3,AverageRating.getAverageRating(ratings));
		
	}
	
}
