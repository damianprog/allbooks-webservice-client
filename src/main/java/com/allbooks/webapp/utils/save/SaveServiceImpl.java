package com.allbooks.webapp.utils.save;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.ResizePhoto;

@Service
public class SaveServiceImpl implements SaveService {

	@Autowired
	BookService bookService;

	@Autowired
	ReaderService readerService;

	@Override
	public void saveRating(Rating rating, boolean update, String bookName, String username) {

		Reader reader = readerService.getReaderByUsername(username);
		rating.setReader(reader);
		
		if (update) 
			bookService.submitRating(rating);
		 else {
			rating.setBookId(bookService.getBookId(bookName));
			rating.setReaderIdentity(reader.getId());
			bookService.submitRating(rating);
		}

	}

	@Override
	public void saveReaderBook(String bookName, boolean update, ReaderBook readerBook, String username)
			throws IOException {
		Reader reader = readerService.getReaderByUsername(username);

		Book book = bookService.getBook(bookService.getBookId(bookName));

		byte[] bookPic = ResizePhoto.resize(book.getBookPhoto(), 125, 190);

		readerBook.setBookPic(bookPic);

		double rating = bookService.getOverallRating(bookName);

		if (update == false) {
			//next utils
			LocalDate localDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String date = localDate.format(formatter);

			readerBook.setBookId(bookService.getBookId(bookName));
			readerBook.setDateAdded(date);
			readerBook.setMinBookName(book.getMiniTitle());
			readerBook.setFullBookName(book.getFullTitle());
			readerBook.setAuthor(book.getAuthor());
			readerBook.setRating(rating);
			readerBook.setReader(reader);

			bookService.saveReaderBook(readerBook);
		} else {
			readerBook.setReader(reader);
			bookService.saveReaderBook(readerBook);
		}
	}

	@Override
	public void saveReview(Review review, String username, String bookName) {
		Reader reader = readerService.getReaderByUsername(username);
		int readerId = reader.getId();

		review.setReaderIdentity(readerId);
		review.setReaderLogin(reader.getUsername());
		review.setRating(bookService.getReaderRatingObject(readerId, bookName));
		review.setBookId(bookService.getBookId(bookName));
		review.setBookTitle(bookName);

		bookService.submitReview(review);

	}

}
