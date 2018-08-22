package com.allbooks.webapp.utils.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Token;
import com.allbooks.webapp.enumeration.TokenType;
import com.allbooks.webapp.utils.TokenCreator;
import com.allbooks.webapp.utils.TokenUrlCreator;
import com.allbooks.webapp.validators.ExpiredTokenRemover;

@Service
public class TokenUtilsServiceImpl implements TokenUtilsService{

	@Autowired
	private TokenCreator tokenCreator;
	
	@Autowired
	private TokenUrlCreator tokenUrlCreator;
	
	@Autowired
	private ExpiredTokenRemover expiredTokenRemover;
	
	@Override
	public Token createTokenForReader(Reader reader, TokenType tokenType) {
		return tokenCreator.createTokenForReader(reader, tokenType);
	}

	@Override
	public String getTokenUrl(Token token) {
		return tokenUrlCreator.getTokenUrl(token);
	}

	@Override
	public boolean isTokenExpiredAndRemoved(Token token) {
		return expiredTokenRemover.isTokenExpired(token);
	}

}
