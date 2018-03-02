package com.allbooks.webapp.entity;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

public class OnRegistrationCompleteEvent extends ApplicationEvent {


	private Locale locale;

	private Reader reader;

	public OnRegistrationCompleteEvent(Reader reader, Locale locale) {
		super(reader);

		this.reader = reader;
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

}
