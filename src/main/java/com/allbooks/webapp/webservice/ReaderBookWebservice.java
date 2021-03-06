package com.allbooks.webapp.webservice;

import org.springframework.data.domain.Page;

import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.enumeration.ShelvesState;

public interface ReaderBookWebservice {

	void saveReaderBook(ReaderBook readerBook);

	ReaderBook getReaderBook(int bookId, int userId);

	ReaderBook[] getReaderBooks(int id);

	ReaderBook getReaderBookById(int readerBookId);

	void updateReaderBook(ReaderBook readerBook);

	void deleteReaderBookByReaderIdAndBookId(int readerId, int bookId);

	ReaderBook[] getReaderBooksByShelves(int readerId, ShelvesState shelvesStates);

	Page<ReaderBook> getReaderBooksPages(int readerId, int page);

	Page<ReaderBook> getReaderBooksByShelvesPages(int readerId, ShelvesState shelvesStates, int page);

	ReaderBook[] get10LatestReaderBooks();

	ReaderBook[] getReaderBooksByCategory(int readerId, String category);

	int[] getReaderBooksBooksIdsByReaderIdAndCategory(int readerId, String category);

	ReaderBook[] get10LatestReaderBooksByReaderId(int readerId);

}
