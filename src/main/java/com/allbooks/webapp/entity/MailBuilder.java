package com.allbooks.webapp.entity;

import org.springframework.beans.factory.annotation.Value;

public class MailBuilder {

	private Reader reader;
	private String subject;
	private String subjectHeader;
	private String subjectMessage;
	private String templateName;
	private TokenType tokenType;


	public enum TokenType {
		CHANGE_PASSWORD, REGISTRATION_CONFIRM;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubjectHeader() {
		return subjectHeader;
	}

	public void setSubjectHeader(String subjectHeader) {
		this.subjectHeader = subjectHeader;
	}

	public String getSubjectMessage() {
		return subjectMessage;
	}

	public void setSubjectMessage(String subjectMessage) {
		this.subjectMessage = subjectMessage;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}

}
