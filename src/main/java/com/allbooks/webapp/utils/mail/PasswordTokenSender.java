package com.allbooks.webapp.utils.mail;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Token;
import com.allbooks.webapp.enumeration.TokenResponse;
import com.allbooks.webapp.enumeration.TokenType;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.TokenService;
import com.allbooks.webapp.utils.service.EmailService;

@Component
public class PasswordTokenSender {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ReaderService readerService;
	
	public TokenResponse tryToSendAndGetResponse(String email) throws MessagingException {
		
		Reader reader = readerService.getReaderByEmail(email + ".com");

		if (reader == null) {
			return TokenResponse.EMAIL_ERROR;
		}

		Token token = tokenService.getTokenByReaderId(reader.getId(),TokenType.PASSWORD_TOKEN);

		if (token != null) {
			return TokenResponse.ALREADY_SENT;
		}

		emailService.sendPasswordChanging(reader);

		return TokenResponse.TOKEN_SENT;
	}

}
