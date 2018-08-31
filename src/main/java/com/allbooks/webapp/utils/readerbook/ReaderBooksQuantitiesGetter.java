package com.allbooks.webapp.utils.readerbook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.service.ReaderBookService;

@Component
public class ReaderBooksQuantitiesGetter {

	@Autowired
	private ReaderBookService readerBookService;

	private int read, wantToRead, currentlyReading;

	private List<ReaderBook> readerBooks;

	public Map<String, Integer> getByReaderId(int readerId) {

		initializeThisFields(readerId);

		initializeQuantities();

		return getQuantitiesMap();
	}

	private void initializeQuantities() {
		for (ReaderBook readerBook : readerBooks) {

			switch (readerBook.getShelvesStates()) {

			case READ:
				read++;
				break;

			case CURRENTLY_READING:
				currentlyReading++;
				break;

			case WANT_TO_READ:
				wantToRead++;
				break;

			}

		}
	}

	private void initializeThisFields(int readerId) {
		read = wantToRead = currentlyReading = 0;
		readerBooks = readerBookService.getReaderBooks(readerId);
	}

	private Map<String, Integer> getQuantitiesMap() {
		Map<String, Integer> readerBooksQuantities = new HashMap<>();
		readerBooksQuantities.put("read", read);
		readerBooksQuantities.put("wantToRead", wantToRead);
		readerBooksQuantities.put("currentlyReading", currentlyReading);
		readerBooksQuantities.put("all", read + wantToRead + currentlyReading);
		return readerBooksQuantities;
	}

}
