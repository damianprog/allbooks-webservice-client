package com.allbooks.webapp.utils.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
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

		modelMap.addAttribute("challangeReader", getChallangeReaderAndEncodeProfilePhoto(readerId));
		modelMap.addAttribute("readBooks", getCurrentYearBooksWithEncodedBookPhotos(readerId));
		modelMap.addAttribute("readingChallange", checkAndGetReadingChalllange(readerId));
		modelMap.addAttribute("readingChallangeComments", readerPostsGetter.getPreparedReadingChallangeComments(readerId));
		
		return modelMap;
	}

	private List<ReaderBook> getCurrentYearBooksWithEncodedBookPhotos(int readerId) {
		List<ReaderBook> currentYearBooks = currentYearReadBooksGetter.getBooks(readerId);
		
		photoService.encodeAndResizeBookPhotoInBookChildren(currentYearBooks, 100, 150);
		return currentYearBooks;
	}

	private Reader getChallangeReaderAndEncodeProfilePhoto(int readerId) {
		Reader challangeReader = readerService.getReaderById(readerId);
		
		photoService.setResizedAndEncodedPhotoInReader(challangeReader, 80, 80);
		return challangeReader;
	}

	private ReadingChallange checkAndGetReadingChalllange(int readerId) {

		ReadingChallange readingChallange = readingChallangeService.getReadingChallangeByReaderId(readerId);

		if (readingChallange == null)
			throw new NotFoundException();

		return readingChallange;
	}

}
