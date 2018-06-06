package com.allbooks.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rating {

	private int id;

	private int rate;

	private int readerId;

	private int bookId;

	public Rating() {
	}

	public int getReaderId() {
		return readerId;
	}

	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	@Override
	public String toString() {
		return "Rating [id=" + id + ", rate=" + rate + ", readerId=" + readerId + ", bookId=" + bookId + "]";
	}

}
