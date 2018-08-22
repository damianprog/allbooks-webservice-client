package com.allbooks.webapp.webservice;

import com.allbooks.webapp.entity.Token;
import com.allbooks.webapp.enumeration.TokenType;
import com.allbooks.webapp.utils.entity.TokenData;

public interface TokenWebservice {

	Token saveToken(Token token);

	Token getTokenByReaderId(int readerId,TokenType tokenType);

	void deleteTokenByReaderId(int readerId,TokenType tokenType);

	void deleteTokenById(int tokenId);

	void updateToken(Token token);

	Token getTokenByCredentials(TokenData tokenData);

}
