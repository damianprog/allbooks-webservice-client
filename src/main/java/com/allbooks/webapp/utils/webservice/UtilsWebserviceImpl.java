package com.allbooks.webapp.utils.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.webservice.BookWebservice;

@Service
public class UtilsWebserviceImpl implements UtilsWebservice {

	@Autowired
	BookWebservice bookWebservice;

	@Override
	public int getBookId(String bookName) {

		
		int bookId = bookWebservice.getBookByName(bookName).getId();

		return bookId;
	}

	@Override
	public String getBookName(int bookId) {
		String bookName = bookWebservice.getBook(bookId).getMiniTitle();

		return bookName;
	}

}
