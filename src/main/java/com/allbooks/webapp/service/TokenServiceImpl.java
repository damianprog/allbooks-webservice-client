package com.allbooks.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Token;
import com.allbooks.webapp.enumeration.TokenType;
import com.allbooks.webapp.utils.entity.TokenData;
import com.allbooks.webapp.webservice.TokenWebservice;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private TokenWebservice tokenWebservice;

	@Override
	public Token getTokenByReaderId(int readerId, TokenType tokenType) {

		return tokenWebservice.getTokenByReaderId(readerId, tokenType);
	}

	@Override
	public void deleteTokenByReaderId(int readerId, TokenType tokenType) {

		tokenWebservice.deleteTokenByReaderId(readerId, tokenType);
	}

	@Override
	public void deleteTokenById(int tokenId) {

		tokenWebservice.deleteTokenById(tokenId);
	}

	@Override
	public void updateToken(Token verificationToken) {

		tokenWebservice.updateToken(verificationToken);
	}

	@Override
	public Token saveToken(Token token) {
		return tokenWebservice.saveToken(token);
	}

	@Override
	public Token getTokenByCredentials(TokenData tokenData) {
		return tokenWebservice.getTokenByCredentials(tokenData);
	}

}
