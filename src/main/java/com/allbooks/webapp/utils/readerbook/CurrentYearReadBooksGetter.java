package com.allbooks.webapp.utils.readerbook;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.enumeration.ShelvesStates;
import com.allbooks.webapp.service.ReaderBookService;

@Component
public class CurrentYearReadBooksGetter {

	@Autowired
	private ReaderBookService readerBookService;
	
	public List<ReaderBook> getBooks(int readerId){
		
		List<ReaderBook> readBooks = readerBookService.getReaderBooksByShelves(readerId, ShelvesStates.READ);
		
		for(ReaderBook rb : readBooks) {
			
			if(rb.getDateRead() == null)
				continue;
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(rb.getDateRead());
			
			if(calendar.get(Calendar.YEAR) != 2018) {
				readBooks.remove(rb);
			}
		}
		
		return readBooks;
		
	}
	
}
