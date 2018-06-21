package com.allbooks.webapp.utils.bookactions;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Review;

@Component
public class Sorter {

	public List<Review> sortReviewsDescending(List<Review> reviewList){
		
		reviewList.sort(Comparator.comparingInt(Review::getId).reversed());
		
		return reviewList;
		
	}	
}
