package com.allbooks.webapp.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.webservice.ReaderBookWebservice;

@Service
public class ReaderBookServiceImpl implements ReaderBookService{

	@Autowired
	private ReaderBookWebservice readerBookWebservice;
	
	@Autowired
	private ReaderService readerService;
	
	@Override
	public void saveReaderBook(ReaderBook readerBook) {

		readerBookWebservice.saveReaderBook(readerBook);

	}

	@Override
	public ReaderBook getReaderBook(int bookId, int readerId) {

		return readerBookWebservice.getReaderBook(bookId, readerId);

	}

	@Override
	public List<ReaderBook> getReaderBooks(int id) {

		List<ReaderBook> readerBooksList = Arrays.asList(readerBookWebservice.getReaderBooks(id));

		return readerBooksList;

	}
	
	@Override
	public void deleteReaderBookById(int bookId, String username) {

		Reader reader = readerService.getReaderByUsername(username);

		readerBookWebservice.deleteReaderBookByReaderIdAndBookId(reader.getId(), bookId);
	}

	@Override
	public ReaderBook getReaderBookById(int readerBookId) {

		return readerBookWebservice.getReaderBookById(readerBookId);
	}
	
	@Override
	public void saveReadDate(String dateRead, int bookId, int id) {

		Reader reader = readerService.getReaderById(id);
		ReaderBook readerBook = readerBookWebservice.getReaderBook(bookId, id);
		readerBook.setDateRead(dateRead);
		readerBook.setReader(reader);

		readerBookWebservice.updateReaderBook(readerBook);
	}

	
}
