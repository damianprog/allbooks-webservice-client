package com.allbooks.webapp.utils.entity;

import com.allbooks.webapp.enumeration.ShelvesState;

public class ReaderBookData {

	private int bookId;

	private ShelvesState shelvesState;

	public ReaderBookData(ShelvesState shelvesState,int bookId) {
		this.shelvesState = shelvesState;
		this.bookId = bookId;
	}

	public ReaderBookData() {}
	
	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public ShelvesState getShelvesState() {
		return shelvesState;
	}

	public void setShelvesState(ShelvesState shelvesState) {
		this.shelvesState = shelvesState;
	}

}
