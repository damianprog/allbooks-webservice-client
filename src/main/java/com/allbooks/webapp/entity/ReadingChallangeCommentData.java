package com.allbooks.webapp.entity;

public class ReadingChallangeCommentData {

	private ReadingChallangeComment readingChallangeComment;

	private int challangeReaderId;

	public ReadingChallangeCommentData(ReadingChallangeComment readingChallangeComment, int challangeReaderId) {
		this.readingChallangeComment = readingChallangeComment;
		this.challangeReaderId = challangeReaderId;
	}

	public ReadingChallangeComment getReadingChallangeComment() {
		return readingChallangeComment;
	}

	public void setReadingChallangeComment(ReadingChallangeComment readingChallangeComment) {
		this.readingChallangeComment = readingChallangeComment;
	}

	public int getChallangeReaderId() {
		return challangeReaderId;
	}

	public void setChallangeReaderId(int challangeReaderId) {
		this.challangeReaderId = challangeReaderId;
	}

}
