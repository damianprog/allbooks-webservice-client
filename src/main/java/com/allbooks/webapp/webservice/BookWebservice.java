package com.allbooks.webapp.webservice;

import org.springframework.data.domain.Page;

import com.allbooks.webapp.entity.Book;

public interface BookWebservice {

	Book getBook(int bookId);

	Book getBookByName(String bookname);

	void saveBook(Book book);

	Page<Book> getBooksByCategory(String category, int page);

	Book[] getBooksByPhrase(String phrase);

}
