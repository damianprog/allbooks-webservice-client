package com.allbooks.webapp.utils.saver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.enumeration.ShelvesState;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.entity.ReaderBookData;

@Component
public class ReaderBookSaver {

	@Autowired
	private SecurityContextService contextService;

	@Autowired
	private ReaderBookService readerBookService;

	@Autowired
	private RatingService ratingService;

	@Autowired
	private ReaderService readerService;

	@Autowired
	private BookService bookService;

	private Book book;

	private Reader reader;

	private ShelvesState shelvesState;
	
	private ReaderBook readerBook;
	
	public void save(ReaderBookData readerBookData) {

		initializeThisFields(readerBookData);
		
		initializeReaderBookFields();
		
		readerBookService.saveReaderBook(readerBook);
	}

	private void initializeThisFields(ReaderBookData readerBookData) {
		this.book = bookService.getBookById(readerBookData.getBookId());
		this.reader = readerService.getReaderById(contextService.getLoggedReaderId());
		this.shelvesState = readerBookData.getShelvesState();
		this.readerBook = readerBookService.getReaderBook(book.getId(), reader.getId());
	}
	
	private void initializeReaderBookFields() {
		
		if(readerBook == null)
			createNewReaderBookAndInitializeReaderBookFields();
		
		readerBook.setShelvesStates(shelvesState);
	}

	private void createNewReaderBookAndInitializeReaderBookFields() {
		Rating rating = ratingService.getReaderRatingObject(reader.getId(), book.getId());	
		
		readerBook = new ReaderBook();
			
		readerBook.setBook(book);
		readerBook.setReaderRating(rating);
		readerBook.setReader(reader);
	}

}
