package com.allbooks.webapp.utils.entity;

public class ReadingChallangeCommentData {

	private String challangeCommentText;

	private int challangeReaderId;

	public ReadingChallangeCommentData(String challangeCommentText, int challangeReaderId) {
		this.challangeCommentText = challangeCommentText;
		this.challangeReaderId = challangeReaderId;
	}

	public ReadingChallangeCommentData() {
	}

	public String getChallangeCommentText() {
		return challangeCommentText;
	}

	public void setChallangeCommentText(String challangeCommentText) {
		this.challangeCommentText = challangeCommentText;
	}

	public int getChallangeReaderId() {
		return challangeReaderId;
	}

	public void setChallangeReaderId(int challangeReaderId) {
		this.challangeReaderId = challangeReaderId;
	}

}
