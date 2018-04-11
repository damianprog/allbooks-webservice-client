package com.allbooks.webapp.utils.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.LocalDateGetter;
import com.allbooks.webapp.utils.entity.OnRegistrationCompleteEvent;

@Service
public class SaveServiceImpl implements SaveService {

	@Autowired
	BookService bookService;

	@Autowired
	ReaderService readerService;

	@Autowired
	ApplicationEventPublisher eventPublisher;

	@Autowired
	PhotoServiceImpl photoService;
	
	@Autowired
	LocalDateGetter localDateGetter;

	@Override
	public void saveRating(Rating rating, Map<String,String> params, String username) {

		Reader reader = readerService.getReaderByUsername(username);
		rating.setReader(reader);

		if (Boolean.valueOf(params.get("updateRating")))
			bookService.submitRating(rating);
		else {
			rating.setBookId(bookService.getBookId(params.get("bookName")));
			rating.setReaderIdentity(reader.getId());
			bookService.submitRating(rating);
		}

	}

	@Override
	public void saveReaderBook(ReaderBook readerBook,Map<String,String> params ,String username)
			throws IOException {
		Reader reader = readerService.getReaderByUsername(username);
		Rating rating = bookService.getReaderRatingObject(params.get("bookName"), username);
		Book book = bookService.getBook(bookService.getBookId(params.get("bookName")));

		byte[] bookPic = photoService.resize(book.getBookPhoto(), 125, 190);

		readerBook.setBookPic(bookPic);

		if (Boolean.valueOf(params.get("updateReaderBook")) == false) {
			
			String date = localDateGetter.getLocalDateStamp();

			readerBook.setBook(book);

			readerBook.setDateAdded(date);
			readerBook.setReader(reader);
		}

		if (rating.getId() != 0)
			readerBook.setReaderRating(rating);

		readerBook.setBook(book);
		readerBook.setReader(reader);
		bookService.saveReaderBook(readerBook);

	}

	@Override
	public void saveReview(Map<String,String> params, String username) {
		Reader reader = readerService.getReaderByUsername(username);
		int readerId = reader.getId();

		Review review = new Review();
		review.setTitle(params.get("title"));
		review.setText(params.get("text"));
		
		String bookName = params.get("bookName");
		
		review.setReaderIdentity(readerId);
		review.setReaderLogin(reader.getUsername());
		review.setRating(bookService.getReaderRatingObject(bookName, reader.getUsername()));
		review.setBookId(bookService.getBookId(bookName));
		review.setBookTitle(bookName);

		bookService.submitReview(review);

	}

	@Override
	public boolean saveReader(Reader reader) {
		if (readerService.saveReader(reader)) {
			Reader readerPass = readerService.getReaderByUsername(reader.getUsername());
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(readerPass));
			return true;
		} else
			return false;

	}

}
