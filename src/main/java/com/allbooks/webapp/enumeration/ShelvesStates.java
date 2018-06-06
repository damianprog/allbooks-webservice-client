package com.allbooks.webapp.enumeration;

public enum ShelvesStates {

	READ("Read"), CURRENTLY_READING("Currently Reading"), WANTTOREAD("Want To Read");

	private String shelveState;

	ShelvesStates(String shelveState){
		this.shelveState = shelveState;
	}

	public String shelveState() {
		return shelveState;
	}

}
