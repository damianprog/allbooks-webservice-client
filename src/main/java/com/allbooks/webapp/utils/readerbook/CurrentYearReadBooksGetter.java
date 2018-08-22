package com.allbooks.webapp.utils.readerbook;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.enumeration.ShelvesState;
import com.allbooks.webapp.service.ReaderBookService;

@Component
public class CurrentYearReadBooksGetter {

	@Autowired
	private ReaderBookService readerBookService;
	
	public List<ReaderBook> getBooks(int readerId){
		
		List<ReaderBook> readBooks = readerBookService.getReaderBooksByShelves(readerId, ShelvesState.READ);
		
		List<ReaderBook> currentYearBooks = new ArrayList<>();
		
		for(ReaderBook rb : readBooks) {
			
			if(rb.getDateRead() == null) {
				continue;
			}

			LocalDate localDate = rb.getDateRead().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			if(localDate.getYear() == 2018) {
				currentYearBooks.add(rb);
			}
		}
		
		return currentYearBooks;
		
	}
	
}
