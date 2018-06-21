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
	private TokenWebservice tokenWebservice;

	@Override
	public PasswordToken createPasswordToken(Reader reader, String token) {

		PasswordToken passwordToken = new PasswordToken(token, reader);
		return tokenWebservice.savePasswordToken(passwordToken);

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
	public VerificationToken createVerificationToken(Reader reader, String token) {

		VerificationToken tokenObj = new VerificationToken(token, reader);

		return tokenWebservice.saveVerificationToken(tokenObj);

	}

	@Override
	public VerificationToken getVerificationTokenByReaderId(int readerId) {

		return tokenWebservice.getVerificationTokenByReaderId(readerId);
	}

	@Override
	public void deleteVerificationTokenById(int tokenId) {

		tokenWebservice.deleteVerificationTokenTokenById(tokenId);
	}

	@Override
	public void updateVerificationToken(VerificationToken verificationToken) {

		tokenWebservice.updateVerificationToken(verificationToken);
	}

}
