package com.allbooks.webapp.entity;

import java.util.Date;

import com.allbooks.webapp.enumeration.ShelvesState;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReaderBook implements BookChild {

	private int id;

	private Book book;

	private double overallRating;

	private Rating readerRating;

	private Reader reader;

	private ShelvesState shelvesStates;

	private Date dateRead;

	private Date dateAdded;

	public ReaderBook() {
		this.dateAdded = new Date();
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
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

	public ShelvesState getShelvesStates() {
		return shelvesStates;
	}

	public void setShelvesStates(ShelvesState shelvesStates) {
		this.shelvesStates = shelvesStates;
	}

	public Date getDateRead() {
		return dateRead;
	}

	public void setDateRead(Date dateRead) {
		this.dateRead = dateRead;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
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

	@Override
	public String toString() {
		return "ReaderBook [id=" + id + ", book=" + book + ", overallRating=" + overallRating + ", readerRating="
				+ readerRating + ", reader=" + reader + ", shelvesStates=" + shelvesStates + ", dateRead=" + dateRead
				+ ", dateAdded=" + dateAdded + "]";
	}

}
