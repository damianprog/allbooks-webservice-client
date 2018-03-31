package com.allbooks.webapp.webservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.exceptions.entity.NotFoundException;
import com.allbooks.webapp.utils.entity.HelperPage;

@Service
public class BookWebserviceImpl implements BookWebservice {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${service.url.name}")
	private String serviceUrlName;

	@Override
	public Rating getReaderRatingObject(int bookId, int readerId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);
		params.put("bookId", bookId);

		Rating rating = restTemplate.getForObject(serviceUrlName + "/readers/{readerId}/books/{bookId}/ratings",
				Rating.class, params);

		return rating;
	}

	@Override
	public void submitRating(Rating rating) {

		restTemplate.put(serviceUrlName + "/ratings", rating);

	}

	@Override
	public Rating[] getBookRatings(int bookId) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("bookId", String.valueOf(bookId));

		ResponseEntity<Rating[]> responseEntity = restTemplate.getForEntity(serviceUrlName + "/books/{bookId}/ratings",
				Rating[].class, params);
		Rating[] ratings = responseEntity.getBody();

		return ratings;
	}

	@Override
	public void submitReview(Review review) {

		restTemplate.postForEntity(serviceUrlName + "/reviews", review, Review.class);

	}

	@Override
	public Review[] getBookReviews(int bookId) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("bookId", String.valueOf(bookId));

		ResponseEntity<Review[]> responseEntity = restTemplate.getForEntity(serviceUrlName + "/books/{bookId}/reviews",
				Review[].class, params);

		return responseEntity.getBody();
	}

	@Override
	public int getReaderRating(String username, String bookName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Review getReviewById(int reviewId) {

		Map<String, String> params = new HashMap<String, String>();
		params.put("reviewId", String.valueOf(reviewId));

		Review review = restTemplate.getForObject(serviceUrlName + "/reviews/{reviewId}", Review.class, params);

		return review;
	}

	@Override
	public void submitComment(Comment comment) {
		restTemplate.postForObject(serviceUrlName + "/comments", comment, Comment.class);

	}

	@Override
	public Comment[] getReviewComments(int reviewId) {

		Map<String, String> params = new HashMap<>();
		params.put("reviewId", String.valueOf(reviewId));

		ResponseEntity<Comment[]> responseEntity = restTemplate
				.getForEntity(serviceUrlName + "/reviews/{reviewId}/comments", Comment[].class, params);

		return responseEntity.getBody();
	}

	@Override
	public void saveReaderBook(ReaderBook readerBook) {
		restTemplate.put("http://localhost:9000/readerbooks", readerBook);

	}

	@Override
	public ReaderBook getReaderBook(int bookId, int readerId) {

		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);
		params.put("bookId", bookId);

		ReaderBook readerBook = restTemplate.getForObject(serviceUrlName + "/readers/{readerId}/readerbooks/{bookId}",
				ReaderBook.class, params);

		return readerBook;

	}

	@Override
	public void updateReaderBook(ReaderBook readerBook) {

		restTemplate.put("http://localhost:9000/readerbooks", readerBook);

	}

	@Override
	public ReaderBook[] getReaderBooks(int id) {

		Map<String, String> params = new HashMap<>();
		params.put("readerId", String.valueOf(id));

		ResponseEntity<ReaderBook[]> responseEntity = restTemplate
				.getForEntity(serviceUrlName + "/readers/{readerId}/readerbooks", ReaderBook[].class, params);

		return responseEntity.getBody();
	}

	@Override
	public Book getBook(int bookId) {

		Map<String, String> params = new HashMap<>();
		params.put("bookId", String.valueOf(bookId));

		Book book = restTemplate.getForObject(serviceUrlName + "/books/{bookId}", Book.class, params);

		return book;
	}

	@Override
	public Book getBookByName(String bookname) {

		Map<String, String> params = new HashMap<String, String>();
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
	public void deleteReaderBookByReaderIdAndBookId(int readerId, int bookId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("readerId", readerId);
		params.put("bookId", bookId);

		restTemplate.delete(serviceUrlName + "/readers/{readerId}/readerbooks/books/{bookId}", params);

	}

	@Override
	public Page<Book> getBooksByCategory(String category, int page) {
		Map<String, String> params = new HashMap<>();
		params.put("category", category);
		params.put("page", String.valueOf(page));

		HelperPage responseEntity = restTemplate.getForObject(serviceUrlName + "/books/categories/{category}/{page}",
				HelperPage.class, params);

		return responseEntity;
	}

	@Override
	public ReaderBook getReaderBookById(int readerBookId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("readerBookId", readerBookId);

		ReaderBook readerBook = restTemplate.getForObject(serviceUrlName + "/readerbooks/{readerBookId}",
				ReaderBook.class, params);

		return readerBook;

	}

}
