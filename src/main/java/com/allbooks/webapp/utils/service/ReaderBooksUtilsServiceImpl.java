package com.allbooks.webapp.utils.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.utils.readerbook.ReaderBooksQuantitiesGetter;

@Service
public class ReaderBooksUtilsServiceImpl implements ReaderBooksUtilsService{
	
	@Autowired
	private ReaderBooksQuantitiesGetter readerBooksQuantitiesGetter;
	
	@Override
	public Map<String, Integer> getReaderBooksQuantities(int readerId) {
		
		return readerBooksQuantitiesGetter.getByReaderId(readerId);
	}
	
}
