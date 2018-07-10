package com.allbooks.webapp.entity;

import java.util.Arrays;

import com.allbooks.webapp.enumeration.ShelvesStates;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReaderBook {

	private int id;

	private Book book;

	private byte[] bookPic;

	private String encodedBookPic;

	private double overallRating;

	private Rating readerRating;

	private Reader reader;

	private ShelvesStates shelvesStates;

	private String dateRead;

	private String dateAdded;

	public ReaderBook() {

	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

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

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public ShelvesStates getShelvesStates() {
		return shelvesStates;
	}

	public void setShelvesStates(ShelvesStates shelvesStates) {
		this.shelvesStates = shelvesStates;
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

	public double getOverallRating() {
		return overallRating;
	}

	public void setOverallRating(double overallRating) {
		this.overallRating = overallRating;
	}

	public Rating getReaderRating() {
		return readerRating;
	}

	public void setReaderRating(Rating readerRating) {
		this.readerRating = readerRating;
	}

}
