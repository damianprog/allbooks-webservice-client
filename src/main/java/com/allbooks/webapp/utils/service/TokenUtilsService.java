package com.allbooks.webapp.utils.service;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Token;
import com.allbooks.webapp.enumeration.TokenType;

public interface TokenUtilsService {

	Token createTokenForReader(Reader reader,TokenType tokenType);
	
	String getTokenUrl(Token token);
	
	boolean isTokenExpiredAndRemoved(Token token);
	
}
