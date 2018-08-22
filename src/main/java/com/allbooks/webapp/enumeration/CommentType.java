package com.allbooks.webapp.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum CommentType {

	REVIEW_COMMENT("REVIEW_COMMENT"), CHALLANGE_COMMENT("CHALLANGE_COMMENT");

	private String commentType;

	private static Map<String, CommentType> map = new HashMap<>();

	static {
		for (CommentType type : CommentType.values()) {
			map.put(type.commentType, type);
		}
	}

	CommentType(String commentType) {
		this.commentType = commentType;
	}

	public String commentType() {
		return commentType;
	}

	public static CommentType enumValueOf(String commentType) {
		return map.get(commentType);
	}

}
