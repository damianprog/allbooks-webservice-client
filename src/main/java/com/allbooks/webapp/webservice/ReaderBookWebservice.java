package com.allbooks.webapp.webservice;

import org.springframework.data.domain.Page;

import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.enumeration.ShelvesStates;

public interface ReaderBookWebservice {

	public void saveReaderBook(ReaderBook readerBook);

	public ReaderBook getReaderBook(int bookId, int userId);

	public ReaderBook[] getReaderBooks(int id);
	
	public ReaderBook getReaderBookById(int readerBookId);
	
	public void updateReaderBook(ReaderBook readerBook);
	
	public void deleteReaderBookByReaderIdAndBookId(int readerId,int bookId);

	public ReaderBook[] getReaderBooksByShelves(int readerId, ShelvesStates shelvesStates);

	public Page<ReaderBook> getReaderBooksPages(int readerId, int page);

	public Page<ReaderBook> getReaderBooksByShelvesPages(int readerId, ShelvesStates shelvesStates, int page);
	
}
