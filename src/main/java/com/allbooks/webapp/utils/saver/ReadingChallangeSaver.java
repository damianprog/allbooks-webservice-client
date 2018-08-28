package com.allbooks.webapp.utils.saver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReadingChallange;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReadingChallangeService;

@Component
public class ReadingChallangeSaver {

	@Autowired
	private ReadingChallangeService readingChallangeService;

	@Autowired
	private ReaderService readerService;
	
	@Autowired
	private SecurityContextService contextService;
	
	public void save(int numberOfBooks) {
		
		ReadingChallange readingChallange = createReadingChallange(numberOfBooks);

		readingChallangeService.saveReadingChallange(readingChallange);
	}

	private ReadingChallange createReadingChallange(int numberOfBooks) {
		
		int readerId = contextService.getLoggedReaderId();

		Reader reader = readerService.getReaderById(readerId);

		return new ReadingChallange(reader, numberOfBooks);
		
	}
	
}
