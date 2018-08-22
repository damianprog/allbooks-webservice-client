package com.allbooks.webapp.utils.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.ReaderPost;
import com.allbooks.webapp.enumeration.CommentType;
import com.allbooks.webapp.service.CommentService;
import com.allbooks.webapp.service.ReadingChallangeCommentService;

@Component
public class CommentByTypeGetter {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ReadingChallangeCommentService challangeCommentService;
	
	public ReaderPost getCommentByTypeAndId(CommentType commentType,int commentId) {
		
		switch(commentType) {
		
		case REVIEW_COMMENT:  
			return commentService.getCommentById(commentId);
		
		case CHALLANGE_COMMENT:
			return challangeCommentService.getCommentById(commentId);
		
		default:return null;
	}

	}
	
}
