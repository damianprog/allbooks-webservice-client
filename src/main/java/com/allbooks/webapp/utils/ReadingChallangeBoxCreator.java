package com.allbooks.webapp.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.ReadingChallange;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.service.ReadingChallangeService;
import com.allbooks.webapp.utils.readerbook.CurrentYearReadBooksGetter;

@Component
public class ReadingChallangeBoxCreator {

	@Autowired
	private CurrentYearReadBooksGetter currentYearReadBooksGetter;
	
	@Autowired
	private ReadingChallangeService readingChallangeService;
	
	@Autowired
	private ModelMapFactory modelMapFactory;
	
	public ModelMap create(int readerId) {
		
		ModelMap modelMap = modelMapFactory.createInstance();
		
		ReadingChallange readingChallange = readingChallangeService.getReadingChallangeByReaderId(readerId);

		if (readingChallange != null) {

			List<ReaderBook> readBooks = currentYearReadBooksGetter.getBooks(readerId);

			int readingProgressPercentage = (readBooks.size() * 100) / readingChallange.getNumberOfBooks();

			modelMap.addAttribute("readingProgressPercentage", readingProgressPercentage);
			modelMap.addAttribute("currentNumberOfBooks", readBooks.size());
			modelMap.addAttribute("readingChallange", readingChallange);
		}
		
		return modelMap;
	}
	
}
