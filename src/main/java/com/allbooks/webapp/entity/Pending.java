package com.allbooks.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pending {

	private int id;

	private Reader recipent;

	private Reader sender;

	public Pending() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Reader getRecipent() {
		return recipent;
	}

	public void setRecipent(Reader recipent) {
		this.recipent = recipent;
	}

	public Reader getSender() {
		return sender;
	}

	public void setSender(Reader sender) {
		this.sender = sender;
	}

}
