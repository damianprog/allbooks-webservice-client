package com.allbooks.webapp.utils.service;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.utils.SendMail;
import com.allbooks.webapp.utils.entity.MailData;
import com.allbooks.webapp.utils.entity.MailData.TokenType;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private SendMail sendMail;

	@Autowired
	private MailData mailData;

	@Override
	public void sendRegistrationConfirmation(Reader reader) throws MessagingException {

		mailData.setReader(reader);
		mailData.setSubject("Registration Confirmation");
		mailData.setTokenType(TokenType.REGISTRATION_CONFIRM);
		mailData.setSubjectHeader("Thanks for joining us!");
		mailData.setSubjectMessage("Click on the link below to confirm your account!");
		mailData.setTemplateName("template");

		sendMail.send(mailData);

	}

	@Override
	public void sendPasswordChanging(Reader reader) throws MessagingException {
		
		mailData.setReader(reader);
		mailData.setSubject("Change Password");
		mailData.setTokenType(TokenType.CHANGE_PASSWORD);
		mailData.setSubjectHeader("Change your password");
		mailData.setSubjectMessage("Click on the link below to change your password");
		mailData.setTemplateName("template");

		sendMail.send(mailData);

	}

}
