package com.allbooks.webapp.utils.readerbook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.service.ReaderBookService;

@Component
public class ReaderBooksQuantitiesGetter {

	@Autowired
	private ReaderBookService readerBookService;
	
	public Map<String,Integer> getByReaderId(int readerId){
		
		int read, wantToRead,currentlyReading;
		read = wantToRead = currentlyReading = 0;

		List<ReaderBook> readerBooks = readerBookService.getReaderBooks(readerId);
		
		for (ReaderBook readerBook : readerBooks) {

			switch (readerBook.getShelves()) {

			case "Read":
				read++;
				break;

			case "Currently Reading":
				currentlyReading++;
				break;

			case "Want To Read":
				wantToRead++;
				break;

			}

		}

		Map<String, Integer> readerBooksQuantities = new HashMap<>();
		readerBooksQuantities.put("read", read);
		readerBooksQuantities.put("wantToRead", wantToRead);
		readerBooksQuantities.put("currentlyReading", currentlyReading);

		return readerBooksQuantities;
		
	}
	
}
