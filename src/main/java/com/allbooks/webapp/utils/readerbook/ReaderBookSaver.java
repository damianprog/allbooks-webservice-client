package com.allbooks.webapp.utils.readerbook;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.ReaderBookData;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.service.ReaderService;

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

	public void save(ReaderBookData readerBookData) throws IOException {

		int readerId = contextService.getLoggedReaderId();

		ReaderBook readerBook = readerBookData.getReaderBook();

		Reader reader = readerService.getReaderById(readerId);

		Rating rating = ratingService.getReaderRatingObject(readerId, readerBookData.getBookId());

		Book book = bookService.getBookById(readerBookData.getBookId());

		if (!readerBookData.isItUpdate())
			readerBook.setDateAdded(new Date());

		readerBook.setReaderRating(rating);
		readerBook.setBook(book);
		readerBook.setReader(reader);
		readerBookService.saveReaderBook(readerBook);

	}

}
