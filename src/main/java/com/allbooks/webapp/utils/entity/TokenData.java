package com.allbooks.webapp.utils.entity;

import com.allbooks.webapp.enumeration.TokenType;

public class TokenData {

	private int readerId;
	
	private String token;	

	private TokenType tokenType;

	public TokenData(int readerId, String token, TokenType tokenType) {
		this.token = token;
		this.readerId = readerId;
		this.tokenType = tokenType;
	}

	public TokenData() {}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getReaderId() {
		return readerId;
	}

	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}

}
