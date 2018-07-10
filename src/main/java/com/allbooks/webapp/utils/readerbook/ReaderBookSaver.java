package com.allbooks.webapp.utils.readerbook;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.ReaderBookData;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.LocalDateGetter;
import com.allbooks.webapp.utils.SecurityContextService;
import com.allbooks.webapp.utils.service.PhotoService;

@Component
public class ReaderBookSaver {

	@Autowired
	private PhotoService photoService;

	@Autowired
	private LocalDateGetter localDateGetter;

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

		Book book = bookService.getBook(readerBookData.getBookId());

		if (!readerBookData.isItUpdate()) {

			String date = localDateGetter.getLocalDateStamp();

			readerBook.setDateAdded(date);
		}

		readerBook.setReaderRating(rating);
		readerBook.setBook(book);
		readerBook.setReader(reader);
		readerBook.setBookPic(resizeBookPic(book));

		readerBookService.saveReaderBook(readerBook);

	}

	private byte[] resizeBookPic(Book book) throws IOException {
		byte[] bookPic = photoService.resize(book.getBookPhoto(), 125, 190);

		return bookPic;
	}

}
