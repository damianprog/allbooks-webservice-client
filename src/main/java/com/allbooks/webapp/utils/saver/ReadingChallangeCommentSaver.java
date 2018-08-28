package com.allbooks.webapp.utils.saver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReadingChallangeComment;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReadingChallangeCommentService;
import com.allbooks.webapp.utils.entity.ReadingChallangeCommentData;

@Component
public class ReadingChallangeCommentSaver {

	@Autowired
	private SecurityContextService contextService;

	@Autowired
	private ReaderService readerService;

	@Autowired
	private ReadingChallangeCommentService challangeCommentService;

	private int loggedReaderId;

	private int challangeReaderId;

	private String challangeCommentText;

	public void save(ReadingChallangeCommentData challangeCommentData) {

		initializeThisFields(challangeCommentData);

		ReadingChallangeComment challangeComment = createChallangeCommentAndInitializeFields();

		challangeCommentService.save(challangeComment);

	}

	private ReadingChallangeComment createChallangeCommentAndInitializeFields() {
		ReadingChallangeComment challangeComment = new ReadingChallangeComment();

		Reader loggedReader = readerService.getReaderById(loggedReaderId);

		Reader challangeReader = readerService.getReaderById(challangeReaderId);

		challangeComment.setText(challangeCommentText);
		challangeComment.setPostingReader(loggedReader);
		challangeComment.setChallangeReader(challangeReader);
		
		return challangeComment;
	}

	private void initializeThisFields(ReadingChallangeCommentData challangeCommentData) {
		this.loggedReaderId = contextService.getLoggedReaderId();
		this.challangeReaderId = challangeCommentData.getChallangeReaderId();
		this.challangeCommentText = challangeCommentData.getChallangeCommentText();
	}

}
