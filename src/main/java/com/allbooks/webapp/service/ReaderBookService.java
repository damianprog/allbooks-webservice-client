package com.allbooks.webapp.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.enumeration.ShelvesStates;

public interface ReaderBookService {

	public void saveReaderBook(ReaderBook readerBook);

	public ReaderBook getReaderBook(int bookId, int readerId);

	public void deleteReaderBookById(int readerBookId, String username);

	public ReaderBook getReaderBookById(int readerBookId);

	public void saveReadDate(String dateRead, int bookId, int id);

	public List<ReaderBook> getReaderBooks(int id);

	public List<ReaderBook> getReaderBooksByShelves(int readerId, ShelvesStates shelvesStates);
	
	public Page<ReaderBook> getReaderBooksPages(int readerId,int page);

	public Page<ReaderBook> getReaderBooksByShelvesPages(int readerId, ShelvesStates shelvesStates,int page);

}
