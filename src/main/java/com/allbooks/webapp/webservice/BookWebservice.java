package com.allbooks.webapp.webservice;

import org.springframework.data.domain.Page;

import com.allbooks.webapp.entity.Book;

public interface BookWebservice {

	public Book getBook(int bookId);

	public Book getBookByName(String bookname);

	public void saveBook(Book book);

	public Page<Book> getBooksByCategory(String category, int page);

}
