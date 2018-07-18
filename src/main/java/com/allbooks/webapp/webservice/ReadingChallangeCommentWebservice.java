package com.allbooks.webapp.webservice;

import com.allbooks.webapp.entity.ReadingChallangeComment;

public interface ReadingChallangeCommentWebservice {

	void save(ReadingChallangeComment readingChallangeComment);

	ReadingChallangeComment[] getReadingChallangeCommentsByChallangeReaderId(int challangeReaderId);

	ReadingChallangeComment getCommentById(int commentId);

	void deleteCommentById(int commentId);

}
