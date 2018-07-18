package com.allbooks.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.ReadingChallange;
import com.allbooks.webapp.webservice.ReadingChallangeWebservice;

@Service
public class ReadingChallangeServiceImpl implements ReadingChallangeService{

	@Autowired
	private ReadingChallangeWebservice readingChallangeWebservice;
	
	@Override
	public void saveReadingChallange(ReadingChallange readingChallange) {
		readingChallangeWebservice.saveReadingChallange(readingChallange);
	}

	@Override
	public ReadingChallange getReadingChallangeById(int id) {
		return readingChallangeWebservice.getReadingChallangeById(id);
	}

	@Override
	public ReadingChallange getReadingChallangeByReaderId(int readerId) {
		return readingChallangeWebservice.getReadingChallangeByReaderId(readerId);
	}

	
	
}
