package com.allbooks.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Like {

	private int id;

	private Reader reader;

	@JsonIgnore
	private Review review;

	public Like() {

	}

	public Like(Reader reader) {
		this.reader = reader;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	@Override
	public String toString() {
		return "Like [id=" + id + ", reader=" + reader + ", review=" + review + "]";
	}

}
