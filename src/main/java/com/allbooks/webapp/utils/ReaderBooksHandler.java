package com.allbooks.webapp.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.entity.ReaderBooksState;
import com.allbooks.webapp.utils.service.PhotoServiceImpl;

@Service
public class ReaderBooksHandler {
	
	@Autowired
	private PhotoServiceImpl photoService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private ReaderBookService readerBookService;
	
	public ReaderBooksState getReaderBooksState(int readerId) {
		
		int read, wantToRead;
		read = wantToRead = 0;

		List<ReaderBook> readerBooks = readerBookService.getReaderBooks(readerId);
		
		List<ReaderBook> currentlyReadingList = new ArrayList<ReaderBook>();

		
		for (ReaderBook readerBook : readerBooks) {

			switch (readerBook.getShelves()) {

			case "Read":
				read++;
				break;

			case "Currently Reading":
				readerBook.setEncodedBookPic(photoService.getEncodedImage(readerBook.getBookPic()));
				currentlyReadingList.add(readerBook);
				break;

			case "Want To Read":
				wantToRead++;
				break;

			}

		}

		Map<String, Integer> readerBooksQuantities = new HashMap<>();
		readerBooksQuantities.put("read", read);
		readerBooksQuantities.put("wantToRead", wantToRead);

		return new ReaderBooksState(readerBooksQuantities, currentlyReadingList);

	}

}
