package com.allbooks.webapp.service;

import java.util.List;

import com.allbooks.webapp.entity.ReaderPost;
import com.allbooks.webapp.entity.Comment;

public interface CommentService {

	void submitComment(Comment comment);

	List<Comment> getCommentsByReaderIdAndBookId(int readerId, int bookId);

	List<ReaderPost> getReviewComments(int reviewId);

	Comment getCommentById(int commentId);

	void deleteCommentById(int commentId);

}
