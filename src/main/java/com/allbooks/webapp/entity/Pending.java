package com.allbooks.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pending {

	private int id;

	private int recipentId;

	private int senderId;

	private String recipentLogin;

	private String senderLogin;

	public Pending() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRecipentId() {
		return recipentId;
	}

	public void setRecipentId(int recipentId) {
		this.recipentId = recipentId;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public String getRecipentLogin() {
		return recipentLogin;
	}

	public void setRecipentLogin(String recipentLogin) {
		this.recipentLogin = recipentLogin;
	}

	public String getSenderLogin() {
		return senderLogin;
	}

	public void setSenderLogin(String senderLogin) {
		this.senderLogin = senderLogin;
	}

}
