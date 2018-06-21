package com.allbooks.webapp.utils.mail;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.VerificationToken;
import com.allbooks.webapp.enumeration.VerificationTokenResponses;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.TokenService;

@Component
public class TokenVerification {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private ReaderService readerService;

	public VerificationTokenResponses verifyToken(Reader reader, String token) {

		VerificationToken tokenObj = tokenService.getVerificationTokenByReaderId(reader.getId());

		if(tokenObj == null) {
			return VerificationTokenResponses.INVALID_TOKEN;
		}
		
		if (token.equals(tokenObj.getToken())) {

			Calendar cal = Calendar.getInstance();

			if ((tokenObj.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
				tokenService.deleteVerificationTokenById(tokenObj.getId());
				return VerificationTokenResponses.TOKEN_EXPIRED;
			}

			reader.setEnabled(true);
			readerService.updateReader(reader);
			tokenService.deleteVerificationTokenById(tokenObj.getId());

			return VerificationTokenResponses.VALID_TOKEN;
		} else
			return VerificationTokenResponses.INVALID_TOKEN;

	}

}
