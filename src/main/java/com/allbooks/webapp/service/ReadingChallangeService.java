package com.allbooks.webapp.service;

import com.allbooks.webapp.entity.ReadingChallange;

public interface ReadingChallangeService {

	void saveReadingChallange(ReadingChallange readingChallange);
	
	ReadingChallange getReadingChallangeById(int id);
	
	ReadingChallange getReadingChallangeByReaderId(int readerId);
	
}
