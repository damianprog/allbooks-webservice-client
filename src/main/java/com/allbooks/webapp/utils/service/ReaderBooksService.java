package com.allbooks.webapp.utils.service;

import java.util.List;
import java.util.Map;

import com.allbooks.webapp.entity.ReaderBook;

public interface ReaderBooksService {

	public Map<String, Integer> getReaderBooksQuantities(int readerId);

	public List<ReaderBook> getCurrentlyReadingBooks(int readerId);
	
}
