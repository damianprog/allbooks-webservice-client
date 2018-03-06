package com.allbooks.webapp.entity;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerificationToken {

	private int id;

	private String token;

	private Reader reader;
	
	public VerificationToken(String token, Reader reader) {
		this.token = token;
		this.reader = reader;
	}

	public VerificationToken() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

}
