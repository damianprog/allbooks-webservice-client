package com.allbooks.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReadingChallangeComment implements ReaderPost {

	private int id;

	private String text;

	private Reader postingReader;

	private Reader challangeReader;

	public ReadingChallangeComment() {
	}

	public ReadingChallangeComment(String text, Reader postingReader, Reader challangeReader) {
		this.text = text;
		this.postingReader = postingReader;
		this.challangeReader = challangeReader;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Reader getPostingReader() {
		return postingReader;
	}

	public void setPostingReader(Reader postingReader) {
		this.postingReader = postingReader;
	}

	public Reader getChallangeReader() {
		return challangeReader;
	}

	public void setChallangeReader(Reader challangeReader) {
		this.challangeReader = challangeReader;
	}

}
