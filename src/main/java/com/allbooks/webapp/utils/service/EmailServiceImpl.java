package com.allbooks.webapp.utils.service;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.utils.SendMail;
import com.allbooks.webapp.utils.entity.MailBuilder;
import com.allbooks.webapp.utils.entity.MailBuilder.TokenType;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	SendMail sendMail;

	@Autowired
	MailBuilder mailBuilder;

	@Override
	public void sendRegistrationConfirmation(Reader reader) throws MessagingException {

		mailBuilder.setReader(reader);
		mailBuilder.setSubject("Registration Confirmation");
		mailBuilder.setTokenType(TokenType.REGISTRATION_CONFIRM);
		mailBuilder.setSubjectHeader("Thanks for joining us!");
		mailBuilder.setSubjectMessage("Click on the link below to confirm your account!");
		mailBuilder.setTemplateName("template");

		sendMail.send(mailBuilder);

	}

	@Override
	public void sendPasswordChanging(Reader reader) throws MessagingException {
		
		mailBuilder.setReader(reader);
		mailBuilder.setSubject("Change Password");
		mailBuilder.setTokenType(TokenType.CHANGE_PASSWORD);
		mailBuilder.setSubjectHeader("Change your password");
		mailBuilder.setSubjectMessage("Click on the link below to change your password");
		mailBuilder.setTemplateName("template");

		sendMail.send(mailBuilder);

	}

}
