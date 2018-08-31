package com.allbooks.webapp.utils.readerbook;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.enumeration.ShelvesState;
import com.allbooks.webapp.service.ReaderBookService;

@Component
public class CurrentYearReadBooksGetter {

	@Autowired
	private ReaderBookService readerBookService;

	private List<ReaderBook> readBooks;

	List<ReaderBook> currentYearBooks;

	public List<ReaderBook> getBooks(int readerId) {

		initializeThisFields(readerId);

		initializeCurrentYearBooksList();

		return currentYearBooks;

	}

	private void initializeThisFields(int readerId) {
		this.readBooks = readerBookService.getReaderBooksByShelves(readerId, ShelvesState.READ);
		this.currentYearBooks = new ArrayList<>();
	}

	private void initializeCurrentYearBooksList() {
		for (ReaderBook rb : readBooks) {

			if (rb.getDateRead() == null) {
				continue;
			}

			addCurrentYearBookToList(rb);
		}
	}

	private void addCurrentYearBookToList(ReaderBook rb) {
		LocalDate localDate = rb.getDateRead().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		if (localDate.getYear() == 2018) {
			currentYearBooks.add(rb);
		}
	}
}
