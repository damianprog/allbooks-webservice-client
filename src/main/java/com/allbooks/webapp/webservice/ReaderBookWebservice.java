package com.allbooks.webapp.webservice;

import com.allbooks.webapp.entity.ReaderBook;

public interface ReaderBookWebservice {

	public void saveReaderBook(ReaderBook readerBook);

	public ReaderBook getReaderBook(int bookId, int userId);

	public ReaderBook[] getReaderBooks(int id);
	
	public ReaderBook getReaderBookById(int readerBookId);
	
	public void updateReaderBook(ReaderBook readerBook);
	
	public void deleteReaderBookByReaderIdAndBookId(int readerId,int bookId);
	
}
