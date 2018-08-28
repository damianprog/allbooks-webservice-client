package com.allbooks.webapp.utils.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Token;
import com.allbooks.webapp.enumeration.TokenType;
import com.allbooks.webapp.utils.entity.MailData;
import com.allbooks.webapp.utils.token.TokenCreator;

@Service
public class SendMail {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailBuilder mailBuilder;
	
	@Autowired
	private TokenCreator tokenCreator;
	
	public void send(MailData mailData) throws MessagingException {

		MimeMessage mail = null;
		
		Reader reader = mailData.getReader();

		switch (mailData.getMailType()) {

		case REGISTRATION_CONFIRM:
			Token verificationToken = tokenCreator.createTokenForReader(reader, TokenType.VERIFICATION_TOKEN);
			mailData.setToken(verificationToken);
			mail = mailBuilder.createTokenMail(mailData);
			break;
		case CHANGE_PASSWORD:
			Token passwordToken = tokenCreator.createTokenForReader(reader,TokenType.PASSWORD_TOKEN);
			mailData.setToken(passwordToken);
			mail = mailBuilder.createTokenMail(mailData);
			break;
		case INFORMATION:
			mail = mailBuilder.createSimpleMail(mailData);
			break;
			
		}		 

		mailSender.send(mail);
	}
	
}
