package com.allbooks.webapp.utils.mail;

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
		MimeMessage mail = null;
		
		Reader reader = mailData.getReader();

		switch (mailData.getMailType()) {

		case REGISTRATION_CONFIRM:
			tokenService.createVerificationToken(reader, token);
			methodMapping = "/readerAccount/registrationConfirm";
			mail = mailBuilder.createTokenMail(mailData, methodMapping);
			break;
		case CHANGE_PASSWORD:
			tokenService.createPasswordToken(reader, token);
			methodMapping = "/readerAccount/changePasswordPage";
			mail = mailBuilder.createTokenMail(mailData, methodMapping);
			break;
		case INFORMATION:
			mail = mailBuilder.createSimpleMail(mailData);
			break;
			
		}

		 

		mailSender.send(mail);
	}

}
