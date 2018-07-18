package com.allbooks.webapp.utils.bookactions;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.ReaderPost;

@Component
public class Sorter {

	public void sortBookActionPostsDescending(List<ReaderPost> list){
		
		list.sort(Comparator.comparingInt(ReaderPost::getId).reversed());
		
	}	
}
