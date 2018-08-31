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

	private ModelMap modelMap;

	private int readerId;

	private ReadingChallange readingChallange;

	public ModelMap create(int readerId) {

		initializeThisFields(readerId);

		intializeModelMapIfReadingChallangeIsPresent();

		return modelMap;
	}

	private void initializeThisFields(int readerId) {
		this.modelMap = modelMapFactory.createInstance();
		this.readerId = readerId;
		this.readingChallange = readingChallangeService.getReadingChallangeByReaderId(readerId);
	}

	private void intializeModelMapIfReadingChallangeIsPresent() {
		if (readingChallange != null)
			initializeReadingChallangeModel();
	}

	private void initializeReadingChallangeModel() {
		List<ReaderBook> readBooks = currentYearReadBooksGetter.getBooks(readerId);

		int readingProgressPercentage = calculateReadingProgressPercentage(readBooks);

		modelMap.addAttribute("readingProgressPercentage", readingProgressPercentage);
		modelMap.addAttribute("currentNumberOfBooks", readBooks.size());
		modelMap.addAttribute("readingChallange", readingChallange);
	}

	private int calculateReadingProgressPercentage(List<ReaderBook> readBooks) {
		return (readBooks.size() * 100) / readingChallange.getNumberOfBooks();
	}

}
