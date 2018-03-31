package com.allbooks.webapp.webservice;

import org.springframework.data.domain.Page;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.Review;

public interface BookWebservice {

	public Rating getReaderRatingObject(int bookId, int readerId);

	public void submitRating(Rating rating);

	public Rating[] getBookRatings(int bookId);

	public void submitReview(Review review);

	public Review[] getBookReviews(int bookId);

	public int getReaderRating(String username, String bookName);

	public Review getReviewById(int reviewId);

	public void submitComment(Comment comment);

	public Comment[] getReviewComments(int reviewId);

	public void saveReaderBook(ReaderBook readerBook);

	public ReaderBook getReaderBook(int bookId, int readerId);

	public void updateReaderBook(ReaderBook readerBook);

	public ReaderBook[] getReaderBooks(int id);

	public Book getBook(int bookId);

	public Book getBookByName(String bookname);

	public void saveBook(Book book);

	public void updateReview(Review review);

	public void deleteReviewById(int reviewId) ;

	public void deleteReaderBookByReaderIdAndBookId(int readerId,int bookId);

	public Page<Book> getBooksByCategory(String category, int page);

	public ReaderBook getReaderBookById(int readerBookId);

}
