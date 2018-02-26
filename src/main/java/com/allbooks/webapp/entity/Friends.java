package com.allbooks.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Friends {

	private int id;

	private int reader1;

	private int reader2;

	private String reader1Login;

	private String reader2Login;

	
	public Friends(int reader1, int reader2) {
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

	public int getReader1() {
		return reader1;
	}

	public void setReader1(int reader1) {
		this.reader1 = reader1;
	}

	public int getReader2() {
		return reader2;
	}

	public void setReader2(int reader2) {
		this.reader2 = reader2;
	}

	public String getReader1Login() {
		return reader1Login;
	}

	public void setReader1Login(String reader1Login) {
		this.reader1Login = reader1Login;
	}

	public String getReader2Login() {
		return reader2Login;
	}

	public void setReader2Login(String reader2Login) {
		this.reader2Login = reader2Login;
	}
}
