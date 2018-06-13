package com.allbooks.webapp.utils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.service.TokenService;
import com.allbooks.webapp.utils.entity.MailData;

@Service
public class SendMail {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private MailBuilder mailBuilder;
	
	public void send(MailData mailData) throws MessagingException {

		String methodMapping = "";
		String token = mailData.getToken();

		Reader reader = mailData.getReader();

		switch (mailData.getTokenType()) {

		case REGISTRATION_CONFIRM:
			tokenService.createVerificationToken(reader, token);
			methodMapping = "/readerAccount/registrationConfirm";
			break;
		case CHANGE_PASSWORD:
			tokenService.createPasswordToken(reader, token);
			methodMapping = "/readerAccount/changePasswordPage";
			break;
		}

		MimeMessage mail = mailBuilder.createMail(mailData, methodMapping);

		mailSender.send(mail);
	}

}
