package com.allbooks.webapp.utils;

import java.io.IOException;

import javax.servlet.http.HttpSession;

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
import com.allbooks.webapp.utils.service.PhotoService;

@Component
public class ReaderBookSaver {

	@Autowired
	private PhotoService photoService;

	@Autowired
	private LocalDateGetter localDateGetter;

	@Autowired
	private HttpSession session;

	@Autowired
	private ReaderBookService readerBookService;

	@Autowired
	private RatingService ratingService;

	@Autowired
	private ReaderService readerService;

	@Autowired
	private BookService bookService;

	public void save(ReaderBookData readerBookData) throws IOException {

		int readerId = (int) session.getAttribute("readerId");

		ReaderBook readerBook = readerBookData.getReaderBook();

		Reader reader = readerService.getReaderById(readerId);

		Rating rating = ratingService.getReaderRatingObject(readerId, readerBookData.getBookId());

		Book book = bookService.getBook(readerBookData.getBookId());

		addBookPicToReaderBook(readerBook, book);

		if (!readerBookData.isItUpdate()) {

			String date = localDateGetter.getLocalDateStamp();

			readerBook.setDateAdded(date);
		}

		readerBook.setReaderRating(rating);
		readerBook.setBook(book);
		readerBook.setReader(reader);

		readerBookService.saveReaderBook(readerBook);

	}

	private void addBookPicToReaderBook(ReaderBook readerBook, Book book) throws IOException {
		byte[] bookPic = photoService.resize(book.getBookPhoto(), 125, 190);

		readerBook.setBookPic(bookPic);
	}

}
