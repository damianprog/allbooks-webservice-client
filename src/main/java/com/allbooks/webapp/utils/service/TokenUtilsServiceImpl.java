package com.allbooks.webapp.utils.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Token;
import com.allbooks.webapp.enumeration.TokenType;
import com.allbooks.webapp.utils.token.ExpiredTokenRemover;
import com.allbooks.webapp.utils.token.InvitationTokenGetter;
import com.allbooks.webapp.utils.token.TokenCreator;
import com.allbooks.webapp.utils.token.TokenUrlCreator;

@Service
public class TokenUtilsServiceImpl implements TokenUtilsService{

	@Autowired
	private TokenCreator tokenCreator;
	
	@Autowired
	private TokenUrlCreator tokenUrlCreator;
	
	@Autowired
	private ExpiredTokenRemover expiredTokenRemover;
	
	@Autowired
	private InvitationTokenGetter invitationTokenGetter;
	
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

	@Override
	public Token getOrCreateInvitationToken() {
		return invitationTokenGetter.get();
	}

}
