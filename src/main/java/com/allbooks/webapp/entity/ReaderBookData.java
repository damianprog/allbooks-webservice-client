package com.allbooks.webapp.entity;

public class ReaderBookData extends BookActionDataObject{

	private ReaderBook readerBook;

	private int bookId;

	private boolean isItUpdate;
	
	public ReaderBookData(ReaderBook readerBook, int bookId, boolean isItUpdate) {
		this.readerBook = readerBook;
		this.bookId = bookId;
		this.isItUpdate = isItUpdate;
	}

	public ReaderBook getReaderBook() {
		return readerBook;
	}

	public void setReaderBook(ReaderBook readerBook) {
		this.readerBook = readerBook;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public boolean isItUpdate() {
		return isItUpdate;
	}

	public void setIsItUpdate(boolean isItUpdate) {
		this.isItUpdate = isItUpdate;
	}

}
