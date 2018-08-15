package com.allbooks.webapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.enumeration.ShelvesStates;
import com.allbooks.webapp.webservice.ReaderBookWebservice;

@Service
public class ReaderBookServiceImpl implements ReaderBookService {

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
	public void saveReadDate(Date dateRead, int bookId, int id) {

		Reader reader = readerService.getReaderById(id);
		ReaderBook readerBook = readerBookWebservice.getReaderBook(bookId, id);
		readerBook.setDateRead(dateRead);
		readerBook.setReader(reader);

		readerBookWebservice.updateReaderBook(readerBook);
	}

	@Override
	public List<ReaderBook> getReaderBooksByShelves(int readerId, ShelvesStates shelvesStates) {

		return new ArrayList<ReaderBook>(
				Arrays.asList(readerBookWebservice.getReaderBooksByShelves(readerId, shelvesStates)));

	}

	@Override
	public Page<ReaderBook> getReaderBooksPages(int readerId, int page) {
		return readerBookWebservice.getReaderBooksPages(readerId, page);
	}

	@Override
	public Page<ReaderBook> getReaderBooksByShelvesPages(int readerId, ShelvesStates shelvesStates, int page) {
		return readerBookWebservice.getReaderBooksByShelvesPages(readerId, shelvesStates, page);
	}

	@Override
	public List<ReaderBook> get10LatestReaderBooks() {
		return new ArrayList<ReaderBook>(Arrays.asList(readerBookWebservice.get10LatestReaderBooks()));
	}

	@Override
	public List<ReaderBook> getReaderBooksByCategory(int readerId, String category) {
		return new ArrayList<ReaderBook>(Arrays.asList(readerBookWebservice.getReaderBooksByCategory(readerId, category)));
	}

	@Override
	public int[] getReaderBooksBooksIdsByReaderIdAndCategory(int readerId, String category) {
		
		int[] excludedIds = readerBookWebservice.getReaderBooksBooksIdsByReaderIdAndCategory(readerId,category);
		
		if(excludedIds.length == 0)
			return new int[] {0};
		
		return excludedIds;
	}

}
