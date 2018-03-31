package com.allbooks.webapp.utils.entity;

import java.util.List;
import java.util.Map;

import com.allbooks.webapp.entity.ReaderBook;

public class ReaderBooksState {

	private Map<String, Integer> readerBooksQuantities;

	private List<ReaderBook> currentlyReadingBooks;

	public ReaderBooksState(Map<String, Integer> readerBooksQuantities, List<ReaderBook> currentlyReadingBooks) {
		this.readerBooksQuantities = readerBooksQuantities;
		this.currentlyReadingBooks = currentlyReadingBooks;
	}

	public Map<String, Integer> getReaderBooksQuantities() {
		return readerBooksQuantities;
	}

	public void setReaderBooksQuantities(Map<String, Integer> readerBooksQuantities) {
		this.readerBooksQuantities = readerBooksQuantities;
	}

	public List<ReaderBook> getCurrentlyReadingBooks() {
		return currentlyReadingBooks;
	}

	public void setCurrentlyReadingBooks(List<ReaderBook> currentlyReadingBooks) {
		this.currentlyReadingBooks = currentlyReadingBooks;
	}

}
