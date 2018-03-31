package com.allbooks.webapp.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.exceptions.entity.AccessForbidden;
import com.allbooks.webapp.utils.AverageRating;
import com.allbooks.webapp.utils.entity.HelperPage;
import com.allbooks.webapp.utils.webservice.UtilsWebservice;
import com.allbooks.webapp.webservice.BookWebservice;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookWebservice bookWebservice;

	@Autowired
	ReaderService readerService;

	@Autowired
	private UtilsWebservice utilsWebservice;

	public BookServiceImpl(BookWebservice bookWebservice, ReaderService readerService,UtilsWebservice utilsWebservice) {
		this.bookWebservice = bookWebservice;
		this.readerService = readerService;
		this.utilsWebservice = utilsWebservice;
	}

	@Override
	public int getBookId(String bookName) {

		return utilsWebservice.getBookId(bookName);

	}

	@Override
	public Rating getReaderRatingObject(String bookName, String username) {

		Reader reader = readerService.getReaderByUsername(username);

		Rating rating = bookWebservice.getReaderRatingObject(utilsWebservice.getBookId(bookName), reader.getId());

		if (rating == null)
			rating = new Rating();

		return rating;
	}

	@Override
	public void submitRating(Rating rating) {

		bookWebservice.submitRating(rating);

	}

	@Override
	public double getOverallRating(String bookName) {

		Rating[] ratings = bookWebservice.getBookRatings(utilsWebservice.getBookId(bookName));

		return AverageRating.getAverageRating(ratings);
	}

	@Override
	public void submitReview(Review review) {

		bookWebservice.submitReview(review);

	}

	@Override
	public List<Review> getBookReviews(String bookName) {

		Review[] reviews = bookWebservice.getBookReviews(utilsWebservice.getBookId(bookName));

		List<Review> reviewList = Arrays.asList(reviews);

		if (reviewList != null)
			reviewList.sort(Comparator.comparingInt(Review::getId).reversed());

		return reviewList;
	}

	@Override
	public int getReaderRating(String username, String bookName) {

		Reader reader = readerService.getReaderByUsername(username);

		Rating rating = bookWebservice.getReaderRatingObject(utilsWebservice.getBookId(bookName), reader.getId());

		if (rating == null)
			return 0;

		return rating.getRate();
	}

	@Override
	public void dropLike(int reviewId) {

		Review review = bookWebservice.getReviewById(reviewId);

		review.setLikes(review.getLikes() + 1);

		bookWebservice.updateReview(review);
	}

	@Override
	public Map<String, Integer> ratingsAndReviewsQuantity(String bookName) {

		int bookId = utilsWebservice.getBookId(bookName);

		Rating[] ratings = bookWebservice.getBookRatings(bookId);

		Review[] reviews = bookWebservice.getBookReviews(bookId);

		Map<String, Integer> map = new HashMap<>();
		map.put("ratings", ratings.length);
		map.put("reviews", reviews.length);

		return map;
	}

	@Override
	public String getBookName(int bookId) {

		return utilsWebservice.getBookName(bookId);
	}

	@Override
	public Review getReviewById(int reviewId) {

		return bookWebservice.getReviewById(reviewId);

	}

	@Override
	public void submitComment(Comment comment) {
		bookWebservice.submitComment(comment);
	}

	@Override
	public List<Comment> getReviewComments(int reviewId) {

		List<Comment> commentsList = Arrays.asList(bookWebservice.getReviewComments(reviewId));

		commentsList.sort(Comparator.comparingInt(Comment::getId).reversed());
		
		return commentsList;
	}

	@Override
	public void saveReaderBook(ReaderBook readerBook) {

		bookWebservice.saveReaderBook(readerBook);

	}

	@Override
	public ReaderBook getReaderBook(int bookId, String username) {

		Reader reader = readerService.getReaderByUsername(username);

		ReaderBook readerBook = bookWebservice.getReaderBook(bookId, reader.getId());

		if (readerBook == null)
			readerBook = new ReaderBook();

		return readerBook;

	}

	@Override
	public void updateReaderBookShelves(String shelves, int bookId, int readerId) {

		Reader reader = readerService.getReaderById(readerId);

		ReaderBook readerBook = bookWebservice.getReaderBook(bookId, readerId);

		readerBook.setShelves(shelves);
		readerBook.setReader(reader);

		bookWebservice.updateReaderBook(readerBook);

	}

	@Override
	public List<ReaderBook> getReaderBooks(int id) {

		List<ReaderBook> readerBooksList = Arrays.asList(bookWebservice.getReaderBooks(id));

		return readerBooksList;

	}

	@Override
	public Book getBook(int bookId) {

		return bookWebservice.getBook(bookId);
	}

	@Override
	public void saveReadDate(String dateRead, int bookId, int id) {

		String bookName = utilsWebservice.getBookName(bookId);
		Reader reader = readerService.getReaderById(id);
		ReaderBook readerBook = bookWebservice.getReaderBook(bookId, id);
		readerBook.setDateRead(dateRead);
		readerBook.setReader(reader);

		bookWebservice.updateReaderBook(readerBook);
	}

	@Override
	public Book getBookByName(String bookname) {

		return bookWebservice.getBookByName(bookname);
	}

	@Override
	public void saveBook(Book book) {
		bookWebservice.saveBook(book);
	}

	@Override
	public void updateReview(Review review) {

		bookWebservice.updateReview(review);

	}

	@Override
	public void deleteReviewById(int reviewId) {

		bookWebservice.deleteReviewById(reviewId);
	}

	@Override
	public void deleteReaderBookById(int bookId, String username) {
		
		Reader reader = readerService.getReaderByUsername(username);

		bookWebservice.deleteReaderBookByReaderIdAndBookId(reader.getId(), bookId);
	}

	@Override
	public ReaderBook getReaderBookById(int readerBookId) {

		return bookWebservice.getReaderBookById(readerBookId);
	}

	@Override
	public Page<Book> getBooksByCategory(String category, int page) {

		return bookWebservice.getBooksByCategory(category, page);
	}

}
