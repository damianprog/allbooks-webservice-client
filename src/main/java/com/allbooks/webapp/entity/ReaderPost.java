package com.allbooks.webapp.entity;

public interface ReaderPost {

	int getId();

	void setId(int id);
	
	String getText();

	void setText(String text);
	
	Reader getPostingReader();
	
	void setPostingReader(Reader reader);
	
}
