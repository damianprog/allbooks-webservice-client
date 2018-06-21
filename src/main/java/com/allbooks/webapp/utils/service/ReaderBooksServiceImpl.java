package com.allbooks.webapp.utils.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.utils.readerbook.CurrentlyReadingBooksGetter;
import com.allbooks.webapp.utils.readerbook.ReaderBooksQuantitiesGetter;

@Service
public class ReaderBooksServiceImpl implements ReaderBooksService{
	
	@Autowired
	private ReaderBooksQuantitiesGetter readerBooksQuantitiesGetter;
	
	@Autowired
	private CurrentlyReadingBooksGetter currentlyReadingBooksGetter;
	
	public Map<String, Integer> getReaderBooksQuantities(int readerId) {
		
		return readerBooksQuantitiesGetter.getByReaderId(readerId);
	}

	public List<ReaderBook> getCurrentlyReadingBooks(int readerId){
		
		return currentlyReadingBooksGetter.getByReaderId(readerId);
	}
	
}
