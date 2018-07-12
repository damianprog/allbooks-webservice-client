package com.allbooks.webapp.utils.service;

import javax.mail.MessagingException;

import com.allbooks.webapp.entity.Ban;
import com.allbooks.webapp.entity.Reader;

public interface EmailService {

	void sendVerificationToken(Reader reader) throws MessagingException;

	void sendPasswordChanging(Reader reader) throws MessagingException;

	void sendBanInformation(Ban ban) throws MessagingException;
	
}
