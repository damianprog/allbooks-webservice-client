package com.allbooks.webapp.utils.entity;

import org.springframework.context.ApplicationEvent;

import com.allbooks.webapp.entity.Reader;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

	private Reader reader;

	public OnRegistrationCompleteEvent(Reader reader) {
		super(reader);

		this.reader = reader;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

}
