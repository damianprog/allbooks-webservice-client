package com.allbooks.webapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.ReaderPost;
import com.allbooks.webapp.entity.ReadingChallangeComment;
import com.allbooks.webapp.webservice.ReadingChallangeCommentWebservice;

@Service
public class ReadingChallangeCommentServiceImpl implements ReadingChallangeCommentService {

	@Autowired
	private ReadingChallangeCommentWebservice readingChallangeCommentWebservice;

	@Override
	public void save(ReadingChallangeComment readingChallangeComment) {
		readingChallangeCommentWebservice.save(readingChallangeComment);
	}

	@Override
	public List<ReaderPost> getReadingChallangeCommentsByChallangeReaderId(int challangeReaderId) {
		return new ArrayList<ReaderPost>(Arrays.asList(
				readingChallangeCommentWebservice.getReadingChallangeCommentsByChallangeReaderId(challangeReaderId)));
	}

	@Override
	public ReadingChallangeComment getCommentById(int commentId) {
		return readingChallangeCommentWebservice.getCommentById(commentId);
	}

	@Override
	public void deleteCommentById(int commentId) {
		readingChallangeCommentWebservice.deleteCommentById(commentId);
	}

}
