package com.allbooks.webapp.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.allbooks.webapp.entity.Book;

public interface BookService {

	int getBookId(String bookName);

	String getBookName(int bookId);

	Book getBook(int bookId);

	Book getBookByName(String bookname);

	void saveBook(Book book);

	Page<Book> getBooksByCategory(String category, int page);

	List<Book> getBooksByPhrase(String phrase);

}
