package com.allbooks.webapp.utils.entity;

import com.allbooks.webapp.enumeration.CommentType;

public class CommentRemovalData {

	private CommentType commentType;

	private int commentId;

	private String reasonOfRemoval;

	public CommentRemovalData(CommentType commentType, int commentId, String reasonOfRemoval) {
		this.commentType = commentType;
		this.commentId = commentId;
		this.reasonOfRemoval = reasonOfRemoval;
	}

	public CommentRemovalData() {}
	
	public CommentType getCommentType() {
		return commentType;
	}

	public void setCommentType(CommentType commentType) {
		this.commentType = commentType;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getReasonOfRemoval() {
		return reasonOfRemoval;
	}

	public void setReasonOfRemoval(String reasonOfRemoval) {
		this.reasonOfRemoval = reasonOfRemoval;
	}

}
