package com.allbooks.webapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.enumeration.ShelvesStates;

public interface ReaderBookService {

	void saveReaderBook(ReaderBook readerBook);

	ReaderBook getReaderBook(int bookId, int readerId);

	void deleteReaderBookById(int readerBookId, String username);

	ReaderBook getReaderBookById(int readerBookId);

	void saveReadDate(Date dateRead, int bookId, int id);

	List<ReaderBook> getReaderBooks(int id);

	List<ReaderBook> getReaderBooksByShelves(int readerId, ShelvesStates shelvesStates);

	Page<ReaderBook> getReaderBooksPages(int readerId, int page);

	Page<ReaderBook> getReaderBooksByShelvesPages(int readerId, ShelvesStates shelvesStates, int page);
	
	List<ReaderBook> get10LatestReaderBooks();

	List<ReaderBook> getReaderBooksByCategory(int readerId, String category);

	int[] getReaderBooksBooksIdsByReaderIdAndCategory(int readerId, String category);
	
}
