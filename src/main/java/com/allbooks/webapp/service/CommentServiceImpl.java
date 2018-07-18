package com.allbooks.webapp.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.ReaderPost;
import com.allbooks.webapp.entity.Comment;
import com.allbooks.webapp.utils.bookactions.Sorter;
import com.allbooks.webapp.webservice.CommentWebservice;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentWebservice commentWebservice;
	
	@Autowired
	private Sorter sorter;
	
	@Override
	public void submitComment(Comment comment) {
		commentWebservice.submitComment(comment);
	}

	@Override
	public List<ReaderPost> getReviewComments(int reviewId) {

		List<ReaderPost> commentsList = Arrays.asList(commentWebservice.getReviewComments(reviewId));

		sorter.sortBookActionPostsDescending(commentsList);

		return commentsList;
	}

	@Override
	public List<Comment> getCommentsByReaderIdAndBookId(int readerId, int bookId) {
		
		return Arrays.asList(commentWebservice.getCommentsByReaderIdAndBookId(readerId,bookId)); 
	}

	@Override
	public Comment getCommentById(int commentId) {
		return commentWebservice.getCommentById(commentId);
	}
	
	@Override
	public void deleteCommentById(int commentId) {
		
		commentWebservice.deleteCommentById(commentId);
		
	}

	@Override
	public void deleteCommentByIdAndReaderId(int commentId, int readerId) {

		commentWebservice.deleteCommentByIdAndReaderId(commentId,readerId);
	}
	
}
