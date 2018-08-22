package com.allbooks.webapp.utils.readerbook;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.enumeration.ShelvesState;
import com.allbooks.webapp.factories.ListFactory;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.utils.service.PhotoService;

@Component
public class CurrentlyReadingBooksGetter {

	@Autowired
	private ReaderBookService readerBookService;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private ListFactory listFactory;

	public List<ReaderBook> getByReaderId(int readerId) {

		List<ReaderBook> readerBooks = readerBookService.getReaderBooks(readerId);

		Iterator<ReaderBook> iter = readerBooks.iterator();

		List<ReaderBook> currentlyReadingBooks = listFactory.createArrayList();

		iter.forEachRemaining(e -> {
			if (e.getShelvesStates().equals(ShelvesState.CURRENTLY_READING)) {
				e.getBook().setEncodedBookPhoto(
						photoService.getEncodedImage(photoService.resize(e.getBook().getBookPhoto(), 120, 200)));
				currentlyReadingBooks.add(e);
			}

		});

		return currentlyReadingBooks;
	}

}
