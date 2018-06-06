package com.allbooks.webapp.service;

import org.springframework.data.domain.Page;

import com.allbooks.webapp.entity.Book;

public interface BookService {

	public int getBookId(String bookName);

	public String getBookName(int bookId);

	public Book getBook(int bookId);

	public Book getBookByName(String bookname);

	public void saveBook(Book book);

	public Page<Book> getBooksByCategory(String category, int page);

}
