package com.allbooks.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReaderBook {

	private int id;

	private int bookId;

	private String minBookName;

	private String fullBookName;

	private byte[] bookPic;

	private String encodedBookPic;

	private String author;

	private double rating;

	private int readerRating;

	private int readerIdentity;

	private Reader reader;

	private String shelves;

	private String dateRead;

	private String dateAdded;

	public String getEncodedBookPic() {
		return encodedBookPic;
	}

	public void setEncodedBookPic(String encodedBookPic) {
		this.encodedBookPic = encodedBookPic;
	}

	public byte[] getBookPic() {
		return bookPic;
	}

	public void setBookPic(byte[] bookPic) {
		this.bookPic = bookPic;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getReaderIdentity() {
		return readerIdentity;
	}

	public void setReaderIdentity(int readerIdentity) {
		this.readerIdentity = readerIdentity;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public String getShelves() {
		return shelves;
	}

	public void setShelves(String shelves) {
		this.shelves = shelves;
	}

	public String getDateRead() {
		return dateRead;
	}

	public void setDateRead(String dateRead) {
		this.dateRead = dateRead;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getMinBookName() {
		return minBookName;
	}

	public void setMinBookName(String minBookName) {
		this.minBookName = minBookName;
	}

	public String getFullBookName() {
		return fullBookName;
	}

	public void setFullBookName(String fullBookName) {
		this.fullBookName = fullBookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getReaderRating() {
		return readerRating;
	}

	public void setReaderRating(int readerRating) {
		this.readerRating = readerRating;
	}

}
