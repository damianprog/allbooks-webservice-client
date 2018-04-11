package com.allbooks.webapp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.VerificationToken;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.TokenService;

@Service
public class TokenVerification {

	@Autowired
	TokenService tokenService;

	@Autowired
	ReaderService readerService;

	public boolean verifyToken(Reader reader, String token) {

		VerificationToken tokenObj = tokenService.getVerificationTokenByReaderId(reader.getId());

		if (token.equals(tokenObj.getToken())) {
			reader.setEnabled(true);
			readerService.updateReader(reader);
			tokenService.deleteVerificationTokenById(tokenObj.getId());

			return true;
		} else
			return false;

	}

}
