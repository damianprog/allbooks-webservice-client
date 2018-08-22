package com.allbooks.webapp.utils;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Token;
import com.allbooks.webapp.enumeration.TokenType;
import com.allbooks.webapp.service.TokenService;

@Component
public class TokenCreator {

	@Autowired
	private TokenService tokenService;
	
	public Token createTokenForReader(Reader reader,TokenType tokenType) {
		
		String token = UUID.randomUUID().toString();
		
		Token tokenObj = new Token(reader,token,tokenType);
		
		return tokenService.saveToken(tokenObj);
		
	} 
	
}
