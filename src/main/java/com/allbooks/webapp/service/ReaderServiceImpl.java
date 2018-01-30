package com.allbooks.webapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.Review;

@Service
public class ReaderServiceImpl implements ReaderService {

	@Autowired
	RestTemplate restTemplate;

	@Override
	public boolean saveReader(Reader theReader) {

		if (checkReaderLogin(theReader)) {
			restTemplate.postForObject("http://localhost:9000/readers", theReader, Reader.class);
			return true;
		} else
			return false;
	}

	private boolean checkReaderLogin(Reader theReader) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerLogin", theReader.getLogin());

		Reader reader = restTemplate.getForObject("http://localhost:9000/readers/logins/{readerLogin}", Reader.class,
				params);

		if (reader == null)
			return true;
		else
			return false;

	}

	@Override
	public Reader getReader(String login, String password) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerLogin", login);

		Reader reader = restTemplate.getForObject("http://localhost:9000/readers/logins/{readerLogin}", Reader.class,
				params);

		if (reader != null) {
			if (reader.getPassword().equals(password))
				return reader;
			else
				return null;
		} else
			return null;

	}

	@Override
	public int getBookId(String bookName) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("title", bookName);

		Book book = restTemplate.getForObject("http://localhost:9000/books/title/{title}", Book.class, params);

		int bookId = book.getId();

		return bookId;
	}

	@Override
	public Rating checkIfRated(int id, String bookName) {

		int bookId = getBookId(bookName);

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerId", String.valueOf(id));
		params.put("bookId", String.valueOf(bookId));

		Rating rating = restTemplate.getForObject("http://localhost:9000/readers/{readerId}/books/{bookId}/ratings",
				Rating.class, params);

		return rating;
	}

	@Override
	public void submitRating(Rating rating) {

		restTemplate.put("http://localhost:9000/ratings", rating);

	}

	@Override
	public double getOverallRating(String bookName) {

		int bookId = getBookId(bookName);
		int sum = 0;
		int avg = 0;

		Map<String, String> params = new HashMap<String, String>();
		params.put("bookId", String.valueOf(bookId));

		ResponseEntity<Rating[]> responseEntity = restTemplate
				.getForEntity("http://localhost:9000/books/{bookId}/ratings", Rating[].class, params);
		Rating[] ratings = responseEntity.getBody();

		int ratingsLength = ratings.length;

		for (Rating rating : ratings) {
			sum += rating.getRate();
		}

		avg = sum / ratingsLength;

		return avg;
	}

	@Override
	public void submitReview(Review review) {

		restTemplate.postForObject("http://localhost:9000/reviews", review, Review.class);

	}

	@Override
	public List<Review> getBookReviews(String redirectBookName, String bookName) {

		int bookId;

		if (redirectBookName != null)
			bookId = getBookId(redirectBookName);
		else
			bookId = getBookId(bookName);

		Map<String, String> params = new HashMap<String, String>();
		params.put("bookId", String.valueOf(bookId));

		ResponseEntity<Review[]> responseEntity = restTemplate
				.getForEntity("http://localhost:9000/books/{bookId}/reviews", Review[].class, params);
		Review[] reviews = responseEntity.getBody();
		List<Review> reviewList = new ArrayList<>();

		for (Review review : reviews) {
			reviewList.add(review);
		}

		return reviewList;
	}

	@Override
	public int getReaderRating(int readerId, String bookName) {

		int bookId = getBookId(bookName);

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerId", String.valueOf(readerId));
		params.put("bookId", String.valueOf(bookId));

		Rating rating = restTemplate.getForObject("http://localhost:9000/readers/{readerId}/books/{bookId}/ratings",
				Rating.class, params);

		return rating.getRate();
	}

	@Override
	public void dropLike(int reviewId) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("reviewId", String.valueOf(reviewId));

		Review review = restTemplate.getForObject("http://localhost:9000/reviews/{reviewId}", Review.class, params);

		int reviewLikes = review.getLikes();
		reviewLikes++;
		review.setLikes(reviewLikes);

		restTemplate.put("http://localhost:9000/reviews/{reviewId}", review, params);
	}

	@Override
	public int[] howManyRatesAndReviews(String bookName) {

		int ratingsQuantity = 0;
		int reviewsQuantity = 0;

		int[] ratesAndReviews = new int[2];

		int bookId = getBookId(bookName);

		Map<String, String> params = new HashMap<String, String>();
		params.put("bookId", String.valueOf(bookId));

		ResponseEntity<Rating[]> responseEntity = restTemplate
				.getForEntity("http://localhost:9000/books/{bookId}/ratings", Rating[].class, params);
		Rating[] ratings = responseEntity.getBody();

		ResponseEntity<Review[]> responseEntity2 = restTemplate
				.getForEntity("http://localhost:9000/books/{bookId}/reviews", Review[].class, params);
		Review[] reviews = responseEntity2.getBody();

		for (Rating rating : ratings) {
			ratingsQuantity++;
		}

		for (Review review : reviews) {
			reviewsQuantity++;
		}

		ratesAndReviews[0] = ratingsQuantity;
		ratesAndReviews[1] = reviewsQuantity;

		return ratesAndReviews;
	}

	@Override
	public String getBookName(int bookId) {

		Map<String, String> params = new HashMap<>();
		params.put("bookId", String.valueOf(bookId));

		Book book = restTemplate.getForObject("http://localhost:9000/books/{bookId}", Book.class, params);
		String bookName = book.getTitle();

		return bookName;
	}

	@Override
	public Review getOneReview(int reviewId) {

		Map<String, String> params = new HashMap<>();
		params.put("reviewId", String.valueOf(reviewId));

		Review review = restTemplate.getForObject("http://localhost:9000/reviews/{reviewId}", Review.class, params);

		return review;
	}

	@Override
	public void submitComment(Comment comment) {
		restTemplate.postForObject("http://localhost:9000/comments", comment, Comment.class);
	}

	@Override
	public List<Comment> getReviewComments(int reviewId) {

		Map<String, String> params = new HashMap<>();
		params.put("reviewId", String.valueOf(reviewId));

		ResponseEntity<Comment[]> responseEntity = restTemplate
				.getForEntity("http://localhost:9000/reviews/{reviewId}/comments", Comment[].class, params);
		Comment[] comments = responseEntity.getBody();

		List<Comment> commentsList = new ArrayList<>();

		for (Comment comment : comments)
			commentsList.add(comment);

		return commentsList;
	}

	@Override
	public void saveReaderBook(ReaderBook readerBook) {

		restTemplate.put("http://localhost:9000/readerbooks", readerBook);

	}

	@Override
	public ReaderBook getReaderBook(String bookName, int readerId) {

		int bookId = getBookId(bookName);

		Map<String, String> params = new HashMap<>();
		params.put("readerId", String.valueOf(readerId));
		params.put("bookId", String.valueOf(bookId));

		ReaderBook readerBook = restTemplate.getForObject(
				"http://localhost:9000/readers/{readerId}/readerbooks/{bookId}", ReaderBook.class, params);

		return readerBook;

	}

	@Override
	public void saveNewState(String shelves, int bookId, int readerId) {
		
		Map<String, String> params = new HashMap<>();
		params.put("readerId", String.valueOf(readerId));
		params.put("bookId", String.valueOf(bookId));
		
		ReaderBook readerBook = restTemplate.getForObject("http://localhost:9000/readers/{readerId}/readerbooks/{bookId}", ReaderBook.class,params);
		readerBook.setShelves(shelves);
		
		restTemplate.put("http://localhost:9000/readerbooks", readerBook);

	}

	@Override
	public List<ReaderBook> getReaderBooks(int id) {

		Map<String, String> params = new HashMap<>();
		params.put("readerId", String.valueOf(id));

		ResponseEntity<ReaderBook[]> responseEntity = restTemplate
				.getForEntity("http://localhost:9000/readers/{readerId}/readerbooks", ReaderBook[].class, params);
		ReaderBook[] readerBooks = responseEntity.getBody();

		List<ReaderBook> readerBooksList = new ArrayList<>();

		for (ReaderBook r : readerBooks)
			readerBooksList.add(r);

		return readerBooksList;

	}

	@Override
	public Book getBook(int bookId) {
		
		Map<String, String> params = new HashMap<>();
		params.put("bookId", String.valueOf(bookId));
		
		Book book = restTemplate.getForObject("http://localhost:9000/books/{bookId}", Book.class,params);
		
		return book;
	}

	@Override
	public void saveReadDate(String dateRead, int bookId, int id) {
		
		String bookName = getBookName(bookId);
		
		ReaderBook readerBook = getReaderBook(bookName,id);
		readerBook.setDateRead(dateRead);
		
		restTemplate.put("http://localhost:9000/readerbooks", readerBook);
	}

}
