package com.allbooks.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.PasswordToken;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.VerificationToken;
import com.allbooks.webapp.webservice.TokenWebservice;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	TokenWebservice tokenWebservice;

	@Override
	public void createPasswordToken(Reader reader, String token) {

		PasswordToken passwordToken = new PasswordToken(token, reader);
		tokenWebservice.savePasswordToken(passwordToken);

	}

	@Override
	public PasswordToken getPasswordTokenByReaderId(int readerId) {

		return tokenWebservice.getPasswordTokenByReaderId(readerId);
	}

	@Override
	public PasswordToken getPasswordTokenByCredentials(int readerId, String token) {

		return tokenWebservice.getPasswordTokenByCredentials(readerId, token);
	}

	@Override
	public void deletePasswordTokenByReaderId(int readerId) {

		tokenWebservice.deletePasswordTokenByReaderId(readerId);
	}

	@Override
	public void createVerificationToken(Reader reader, String token) {

		VerificationToken tokenObj = new VerificationToken(token, reader);

		tokenWebservice.saveVerificationToken(tokenObj);

	}

	@Override
	public VerificationToken getVerificationTokenByReaderId(int readerId) {

		return tokenWebservice.getVerificationTokenByReaderId(readerId);
	}

	@Override
	public void deleteVerificationTokenById(int tokenId) {

		tokenWebservice.deleteVerificationTokenTokenById(tokenId);
	}

}
