package com.allbooks.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Friends {

	private int id;

	private Reader reader1;

	private Reader reader2;

	public Friends(Reader reader1, Reader reader2) {
		this.reader1 = reader1;
		this.reader2 = reader2;
	}

	public Friends() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Reader getReader1() {
		return reader1;
	}

	public void setReader1(Reader reader1) {
		this.reader1 = reader1;
	}

	public Reader getReader2() {
		return reader2;
	}

	public void setReader2(Reader reader2) {
		this.reader2 = reader2;
	}

}
