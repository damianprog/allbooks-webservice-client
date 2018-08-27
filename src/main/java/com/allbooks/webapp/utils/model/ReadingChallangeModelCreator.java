package com.allbooks.webapp.utils.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.ReaderPost;
import com.allbooks.webapp.entity.ReadingChallange;
import com.allbooks.webapp.entity.ReadingChallangeComment;
import com.allbooks.webapp.exceptions.entity.NotFoundException;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReadingChallangeService;
import com.allbooks.webapp.utils.photos.ReaderPostsWithPreparedReadersPhotoGetter;
import com.allbooks.webapp.utils.readerbook.CurrentYearReadBooksGetter;
import com.allbooks.webapp.utils.service.PhotoService;

@Component
public class ReadingChallangeModelCreator {

	@Autowired
	private ReaderService readerService;

	@Autowired
	private ReadingChallangeService readingChallangeService;

	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private CurrentYearReadBooksGetter currentYearReadBooksGetter;

	@Autowired
	private ReaderPostsWithPreparedReadersPhotoGetter readerPostsGetter;
	
	@Autowired
	private ModelMapFactory modelMapFactory;
	
	public ModelMap createModel(int readerId) {

		ModelMap modelMap = modelMapFactory.createInstance();
		
		Reader challangeReader = readerService.getReaderById(readerId);

		ReadingChallange readingChallange = checkAndGetReadingChalllange(readerId);
		
		photoService.setResizedAndEncodedPhotoInReader(challangeReader, 80, 80);

		List<ReaderBook> currentYearBooks = currentYearReadBooksGetter.getBooks(readerId);

		List<ReaderPost> readingChallangeComments = readerPostsGetter.getPreparedReadingChallangeComments(readerId);

		photoService.encodeAndResizeBookPhotoInBookChildren(currentYearBooks, 100, 150);
		
		modelMap.addAttribute("readingChallangeComment",new ReadingChallangeComment());
		modelMap.addAttribute("challangeReader", challangeReader);
		modelMap.addAttribute("readBooks", currentYearBooks);
		modelMap.addAttribute("readingChallange", readingChallange);
		modelMap.addAttribute("readingChallangeComments", readingChallangeComments);
		
		return modelMap;
	}

	private ReadingChallange checkAndGetReadingChalllange(int readerId) {

		ReadingChallange readingChallange = readingChallangeService.getReadingChallangeByReaderId(readerId);

		if (readingChallange == null)
			throw new NotFoundException();

		return readingChallange;
	}

}
