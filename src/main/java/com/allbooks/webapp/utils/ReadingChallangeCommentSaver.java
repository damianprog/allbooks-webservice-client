package com.allbooks.webapp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReadingChallangeComment;
import com.allbooks.webapp.entity.ReadingChallangeCommentData;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReadingChallangeCommentService;

@Component
public class ReadingChallangeCommentSaver {

	@Autowired
	private SecurityContextService contextService;
	
	@Autowired
	private ReaderService readerService;
	
	@Autowired
	private ReadingChallangeCommentService challangeCommentService;
	
	public void save(ReadingChallangeCommentData challangeCommentData) {
		
		int loggedReaderId = contextService.getLoggedReaderId();
		
		ReadingChallangeComment challangeComment = challangeCommentData.getReadingChallangeComment();
		
		Reader loggedReader = readerService.getReaderById(loggedReaderId);

		Reader challangeReader = readerService.getReaderById(challangeCommentData.getChallangeReaderId());

		challangeComment.setPostingReader(loggedReader);
		challangeComment.setChallangeReader(challangeReader);
		
		challangeCommentService.save(challangeComment);
		
	}
	
}
