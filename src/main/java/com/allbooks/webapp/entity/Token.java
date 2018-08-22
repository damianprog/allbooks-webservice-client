package com.allbooks.webapp.entity;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import com.allbooks.webapp.enumeration.TokenType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Token {

	private static final int EXPIRATION = 60 * 24;

	private int id;

	private String token;

	private Reader reader;

	private Date expiryDate;

	private TokenType tokenType;

	public void calculateExpiryDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, 43200);
		this.expiryDate = new Date(cal.getTime().getTime());
	}

	public Token(Reader reader,String token,TokenType tokenType) {
		calculateExpiryDate();
		this.token = token;
		this.reader = reader;
		this.tokenType = tokenType;
	}

	public Token() {
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

	public String getTokenString() {
		return token;
	}

	public void setTokenString(String token) {
		this.token = token;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}

}
