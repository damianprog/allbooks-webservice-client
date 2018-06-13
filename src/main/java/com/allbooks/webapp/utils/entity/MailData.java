package com.allbooks.webapp.utils.entity;

import java.util.UUID;

import com.allbooks.webapp.entity.Reader;

public class MailData {

	private Reader reader;
	private String subject;
	private String subjectHeader;
	private String subjectMessage;
	private String templateName;
	private String token;
	private TokenType tokenType;

	public MailData() {
		this.token = UUID.randomUUID().toString();
	}

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
