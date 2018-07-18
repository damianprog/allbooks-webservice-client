package com.allbooks.webapp.service;

import java.util.List;

import com.allbooks.webapp.entity.ReaderPost;
import com.allbooks.webapp.entity.ReadingChallangeComment;

public interface ReadingChallangeCommentService {

	void save(ReadingChallangeComment readingChallangeComment);
	
	List<ReaderPost> getReadingChallangeCommentsByChallangeReaderId(int challangeReaderId);

	ReadingChallangeComment getCommentById(int commentId);

	void deleteCommentById(int commentId);
	
}
