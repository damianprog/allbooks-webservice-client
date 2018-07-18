package com.allbooks.webapp.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Book;
import com.allbooks.webapp.utils.webservice.UtilsWebservice;
import com.allbooks.webapp.webservice.BookWebservice;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookWebservice bookWebservice;
	
	@Autowired
	private UtilsWebservice utilsWebservice;

	@Override
	public int getBookId(String bookName) {

		return utilsWebservice.getBookId(bookName);

	}

	@Override
	public String getBookName(int bookId) {

		return utilsWebservice.getBookName(bookId);
	}

	

	@Override
	public Book getBook(int bookId) {

		return bookWebservice.getBook(bookId);
	}

	@Override
	public Book getBookByName(String bookname) {

		return bookWebservice.getBookByName(bookname);
	}

	@Override
	public void saveBook(Book book) {
		bookWebservice.saveBook(book);
	}

	

	@Override
	public Page<Book> getBooksByCategory(String category, int page) {

		return bookWebservice.getBooksByCategory(category, page);
	}

	@Override
	public List<Book> getBooksByPhrase(String phrase) {
		return Arrays.asList(bookWebservice.getBooksByPhrase(phrase));
	}

}
