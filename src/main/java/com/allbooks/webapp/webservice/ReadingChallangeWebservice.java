package com.allbooks.webapp.webservice;

import com.allbooks.webapp.entity.ReadingChallange;

public interface ReadingChallangeWebservice {

	void saveReadingChallange(ReadingChallange readingChallange);

	ReadingChallange getReadingChallangeById(int id);

	ReadingChallange getReadingChallangeByReaderId(int readerId);

}
