package com.allbooks.webapp.service;

import java.util.List;

import com.allbooks.webapp.entity.ReaderBook;

public interface ReaderBookService {

	public void saveReaderBook(ReaderBook readerBook);

	public ReaderBook getReaderBook(int bookId,int readerId);

	public List<ReaderBook> getReaderBooks(int id);
	
	public void deleteReaderBookById(int readerBookId, String username);
	
	public ReaderBook getReaderBookById(int readerBookId); 
	
	public void saveReadDate(String dateRead, int bookId, int id);
	
}
