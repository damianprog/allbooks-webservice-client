package com.allbooks.webapp.utils.service;

import javax.mail.MessagingException;

import com.allbooks.webapp.entity.Reader;

public interface EmailService {

	public void sendRegistrationConfirmation(Reader reader) throws MessagingException;
	
	public void sendPasswordChanging(Reader reader) throws MessagingException;
	
}
