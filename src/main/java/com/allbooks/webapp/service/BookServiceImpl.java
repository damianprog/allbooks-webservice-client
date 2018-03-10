package com.allbooks.webapp.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.utils.AverageRating;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${service.url.name}")
	private String serviceUrlName;

	@Autowired
	ReaderService readerService;
	
	@Override
	public int getBookId(String bookName) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("title", bookName);
		Book book = restTemplate.getForObject(serviceUrlName + "/books/title/{title}", Book.class, params);

		int bookId = book.getId();

		return bookId;
	}

	@Override
	public Rating getReaderRatingObject(int id, String bookName) {

		int bookId = getBookId(bookName);

		Map<String, String> params = new HashMap<String, String>();
		params.put("readerId", String.valueOf(id));
		params.put("bookId", String.valueOf(bookId));

		Rating rating = restTemplate.getForObject(serviceUrlName + "/readers/{readerId}/books/{bookId}/ratings",
				Rating.class, params);

		return rating;
	}

	@Override
	public void submitRating(Rating rating) {

		restTemplate.put(serviceUrlName + "/ratings", rating);

	}

	@Override
	public double getOverallRating(String bookName) {

		int bookId = getBookId(bookName);

		Map<String, String> params = new HashMap<String, String>();
		params.put("bookId", String.valueOf(bookId));

		ResponseEntity<Rating[]> responseEntity = restTemplate.getForEntity(serviceUrlName + "/books/{bookId}/ratings",
				Rating[].class, params);
		Rating[] ratings = responseEntity.getBody();

		return AverageRating.getAverageRating(ratings);
	}

	@Override
	public void submitReview(Review review) {

		restTemplate.postForEntity(serviceUrlName + "/reviews", review, Review.class);

	}

	@Override
	public List<Review> getBookReviews(String bookName) {

		int bookId = getBookId(bookName);

		Map<String, String> params = new HashMap<String, String>();
		params.put("bookId", String.valueOf(bookId));

		ResponseEntity<Review[]> responseEntity = restTemplate.getForEntity(serviceUrlName + "/books/{bookId}/reviews",
				Review[].class, params);
		List<Review> reviewList = Arrays.asList(responseEntity.getBody());

		return reviewList;
	}

	@Override
	public int getReaderRating(int readerId, String bookName) {

		Rating rating = getReaderRatingObject(readerId, bookName);

		if (rating == null)
			return 0;

		return rating.getRate();
	}

	@Override
	public void dropLike(int reviewId) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("reviewId", String.valueOf(reviewId));

		Review review = restTemplate.getForObject(serviceUrlName + "/reviews/{reviewId}", Review.class, params);

		int reviewLikes = review.getLikes();
		reviewLikes++;
		review.setLikes(reviewLikes);

		restTemplate.put(serviceUrlName + "/reviews", review);
	}

	@Override
	public int[] howManyRatesAndReviews(String bookName) {

		int ratingsQuantity = 0;
		int reviewsQuantity = 0;

		int[] ratesAndReviews = new int[2];

		int bookId = getBookId(bookName);

		Map<String, String> params = new HashMap<String, String>();
		params.put("bookId", String.valueOf(bookId));

		ResponseEntity<Rating[]> responseEntity = restTemplate.getForEntity(serviceUrlName + "/books/{bookId}/ratings",
				Rating[].class, params);
		Rating[] ratings = responseEntity.getBody();

		ResponseEntity<Review[]> responseEntity2 = restTemplate.getForEntity(serviceUrlName + "/books/{bookId}/reviews",
				Review[].class, params);
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

		Book book = restTemplate.getForObject(serviceUrlName + "/books/{bookId}", Book.class, params);
		String bookName = book.getMiniTitle();

		return bookName;
	}

	@Override
	public Review getOneReview(int reviewId) {

		Map<String, String> params = new HashMap<>();
		params.put("reviewId", String.valueOf(reviewId));

		Review review = restTemplate.getForObject(serviceUrlName + "/reviews/{reviewId}", Review.class, params);

		return review;
	}

	@Override
	public void submitComment(Comment comment) {
		restTemplate.postForObject(serviceUrlName + "/comments", comment, Comment.class);
	}

	@Override
	public List<Comment> getReviewComments(int reviewId) {

		Map<String, String> params = new HashMap<>();
		params.put("reviewId", String.valueOf(reviewId));

		ResponseEntity<Comment[]> responseEntity = restTemplate
				.getForEntity(serviceUrlName + "/reviews/{reviewId}/comments", Comment[].class, params);

		List<Comment> commentsList = Arrays.asList(responseEntity.getBody());

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

		ReaderBook readerBook = restTemplate.getForObject(serviceUrlName + "/readers/{readerId}/readerbooks/{bookId}",
				ReaderBook.class, params);

		return readerBook;

	}

	@Override
	public void updateReaderBook(String shelves, int bookId, int readerId) {

		Map<String, String> params = new HashMap<>();
		params.put("readerId", String.valueOf(readerId));
		params.put("bookId", String.valueOf(bookId));

		Reader reader = readerService.getReaderById(readerId);
		
		ReaderBook readerBook = restTemplate.getForObject(serviceUrlName + "/readers/{readerId}/readerbooks/{bookId}",
				ReaderBook.class, params);
		readerBook.setShelves(shelves);
		readerBook.setReader(reader);

		restTemplate.put("http://localhost:9000/readerbooks", readerBook);

	}

	@Override
	public List<ReaderBook> getReaderBooks(int id) {

		Map<String, String> params = new HashMap<>();
		params.put("readerId", String.valueOf(id));

		ResponseEntity<ReaderBook[]> responseEntity = restTemplate
				.getForEntity(serviceUrlName + "/readers/{readerId}/readerbooks", ReaderBook[].class, params);

		List<ReaderBook> readerBooksList = Arrays.asList(responseEntity.getBody());

		return readerBooksList;

	}

	@Override
	public Book getBook(int bookId) {

		Map<String, String> params = new HashMap<>();
		params.put("bookId", String.valueOf(bookId));

		Book book = restTemplate.getForObject(serviceUrlName + "/books/{bookId}", Book.class, params);

		return book;
	}

	@Override
	public void saveReadDate(String dateRead, int bookId, int id) {

		String bookName = getBookName(bookId);
		Reader reader = readerService.getReaderById(id);
		ReaderBook readerBook = getReaderBook(bookName, id);
		readerBook.setDateRead(dateRead);
		readerBook.setReader(reader);

		restTemplate.put(serviceUrlName + "/readerbooks", readerBook);
	}

	@Override
	public Book getBookByName(String bookname) {
		Map<String, String> params = new HashMap<>();
		params.put("title", bookname);

		Book book = restTemplate.getForObject(serviceUrlName + "/books/title/{title}", Book.class, params);

		return book;
	}

	@Override
	public void saveBook(Book book) {
		restTemplate.postForObject(serviceUrlName + "/books", book, Book.class);
	}

	@Override
	public void updateReview(Review review) {
		restTemplate.put(serviceUrlName + "/reviews", review);

	}

	@Override
	public void deleteReviewById(int reviewId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("reviewId", reviewId);

		restTemplate.delete(serviceUrlName + "/reviews/{reviewId}", params);
	}

	@Override
	public void deleteReaderBookById(int id) {

		Map<String, Integer> params = new HashMap<>();
		params.put("readerBookId", id);

		restTemplate.delete(serviceUrlName + "/readerbooks/{readerBookId}", params);
	}

}
