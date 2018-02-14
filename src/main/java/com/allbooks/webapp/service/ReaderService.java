package com.allbooks.webapp.service;

import java.util.List;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.entity.Rating;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.entity.Role;

public interface ReaderService {

	public boolean saveReader(Reader theReader);
	
	public Reader getReader(String login,String password);
	
	public int getBookId(String bookName);

	public Rating checkIfRated(int id,String bookName);

	public void submitRating(Rating rating);

	public double getOverallRating(String bookName);

	public void submitReview(Review review);

	public List<Review> getBookReviews(String redirectBookName, String bookName);

	public int getReaderRating(int readerId, String bookName);

	public void dropLike(int reviewId);

	public int[] howManyRatesAndReviews(String bookName);

	public String getBookName(int bookId);

	public Review getOneReview(int reviewId);

	public void submitComment(Comment comment);

	public List<Comment> getReviewComments(int reviewId);

	public void saveReaderBook(ReaderBook readerBook);

	public ReaderBook getReaderBook(String bookName,int readerId);

	public void saveNewState(String shelves,int bookId,int readerId);

	public List<ReaderBook> getReaderBooks(int id);

	public Book getBook(int bookId);
	
	public Book getBookByName(String bookname);

	public void saveReadDate(String dateRead, int bookId, int id);
	
	public Reader getReaderByUsername(String login);
	
	public void saveBook(Book book);
	
	public void updateReader(Reader reader);

	public void updateReview(Review review);
}
