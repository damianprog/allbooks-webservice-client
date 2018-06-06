package com.allbooks.webapp.utils.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.RatingData;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.ReaderBookData;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.RatingService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.LocalDateGetter;
import com.allbooks.webapp.utils.entity.OnRegistrationCompleteEvent;

@Service
public class SaveServiceImpl implements SaveService {

	@Autowired
	private BookService bookService;

	@Autowired
	private ReaderBookService readerBookService;

	@Autowired
	private ReaderService readerService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private RatingService ratingService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private PhotoServiceImpl photoService;

	@Autowired
	private LocalDateGetter localDateGetter;

	@Autowired
	private HttpSession session;

	@Override
	public void saveRating(RatingData ratingData) {

		int readerId = (int) session.getAttribute("readerId");

		Rating rating = ratingData.getRating();

		if (ratingData.isItUpdate())
			ratingService.submitRating(rating);
		else {
			rating.setBookId(ratingData.getBookId());
			rating.setReaderId(readerId);
			ratingService.submitRating(rating);
		}

	}

	@Override
	public void saveReaderBook(ReaderBookData readerBookData) throws IOException {

		int readerId = (int) session.getAttribute("readerId");

		ReaderBook readerBook = readerBookData.getReaderBook();

		Reader reader = readerService.getReaderById(readerId);
		Rating rating = ratingService.getReaderRatingObject(readerId, readerBookData.getBookId());
		Book book = bookService.getBook(readerBookData.getBookId());

		byte[] bookPic = photoService.resize(book.getBookPhoto(), 125, 190);

		readerBook.setBookPic(bookPic);

		if (!readerBookData.isItUpdate()) {

			String date = localDateGetter.getLocalDateStamp();

			readerBook.setDateAdded(date);
		}

		// if (rating.getId() != 0)
		readerBook.setReaderRating(rating);

		readerBook.setBook(book);
		readerBook.setReader(reader);
		readerBookService.saveReaderBook(readerBook);

	}

	@Override
	public void saveReview(Map<String, String> params, String username) {
		Reader reader = readerService.getReaderByUsername(username);
		int readerId = reader.getId();

		Review review = new Review();
		review.setTitle(params.get("title"));
		review.setText(params.get("text"));

		int bookId = Integer.parseInt(params.get("bookId"));

		Book book = bookService.getBook(bookId);

		review.setBook(book);
		review.setReader(reader);
		review.setRating(ratingService.getReaderRatingObject(readerId, bookId));

		reviewService.submitReview(review);

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
