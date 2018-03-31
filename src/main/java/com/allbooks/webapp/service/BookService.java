package com.allbooks.webapp.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.Review;

public interface BookService {

	public int getBookId(String bookName);

	public Rating getReaderRatingObject(String bookName,String username);

	public void submitRating(Rating rating);

	public double getOverallRating(String bookName);

	public void submitReview(Review review);

	public List<Review> getBookReviews(String bookName);

	public int getReaderRating(String username, String bookName);

	public void dropLike(int reviewId);

	public Map<String,Integer> ratingsAndReviewsQuantity(String bookName);

	public String getBookName(int bookId);

	public Review getReviewById(int reviewId);

	public void submitComment(Comment comment);

	public List<Comment> getReviewComments(int reviewId);

	public void saveReaderBook(ReaderBook readerBook);

	public ReaderBook getReaderBook(int bookId,String username);

	public void updateReaderBookShelves(String shelves,int bookId,int readerId);

	public List<ReaderBook> getReaderBooks(int id);

	public Book getBook(int bookId);
	
	public Book getBookByName(String bookname);

	public void saveReadDate(String dateRead, int bookId, int id);
	
	public void saveBook(Book book);
	
	public void updateReview(Review review);

	public void deleteReviewById(int reviewId);
	
	public void deleteReaderBookById(int readerBookId,String username);

	public Page<Book> getBooksByCategory(String category,int page);
	
	public ReaderBook getReaderBookById(int readerBookId);
	
}
