package com.allbooks.webapp.utils.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.ReadingChallange;
import com.allbooks.webapp.exceptions.entity.NotFoundException;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReadingChallangeCommentService;
import com.allbooks.webapp.service.ReadingChallangeService;
import com.allbooks.webapp.utils.readerbook.CurrentYearReadBooksGetter;

@Component
public class ReadingChallangeModelCreator {

	@Autowired
	private ReaderService readerService;

	@Autowired
	private ReadingChallangeService readingChallangeService;

	@Autowired
	private CurrentYearReadBooksGetter currentYearReadBooksGetter;

	@Autowired
	private ReadingChallangeCommentService readingChallangeCommentService;

	@Autowired
	private ModelMapFactory modelMapFactory;

	public ModelMap createModel(int readerId) {

		ModelMap modelMap = modelMapFactory.createInstance();

		modelMap.addAttribute("readingChallange", checkAndGetReadingChalllange(readerId));
		modelMap.addAttribute("challangeReader", readerService.getReaderById(readerId));
		modelMap.addAttribute("readBooks", currentYearReadBooksGetter.getBooks(readerId));
		modelMap.addAttribute("readingChallangeComments",
				readingChallangeCommentService.getReadingChallangeCommentsByChallangeReaderId(readerId));

		return modelMap;
	}

	private ReadingChallange checkAndGetReadingChalllange(int readerId) {

		ReadingChallange readingChallange = readingChallangeService.getReadingChallangeByReaderId(readerId);

		if (readingChallange == null)
			throw new NotFoundException();

		return readingChallange;
	}

}
