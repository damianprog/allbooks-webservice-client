package com.allbooks.webapp.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification {

	private int id;

	private Reader reader;

	private String text;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;

	public Notification(Reader reader, String text) {

		this.reader = reader;
		this.text = text;
		this.date = new Date();

	}

	public Notification() {

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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
