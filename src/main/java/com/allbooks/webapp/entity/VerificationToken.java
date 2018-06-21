package com.allbooks.webapp.entity;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerificationToken implements Token{

	private static final int EXPIRATION = 60 * 24;

	private int id;

	private String token;

	private Reader reader;

	private Date expiryDate;

	public void calculateExpiryDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, 43200);
		this.expiryDate = new Date(cal.getTime().getTime());
	}

	public VerificationToken(String token, Reader reader) {
		calculateExpiryDate();
		this.token = token;
		this.reader = reader;
	}

	public VerificationToken() {
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public static int getExpiration() {
		return EXPIRATION;
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
