package com.allbooks.webapp.utils.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Token;
import com.allbooks.webapp.enumeration.TokenType;
import com.allbooks.webapp.enumeration.TokenResponse;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.TokenService;
import com.allbooks.webapp.utils.entity.TokenData;
import com.allbooks.webapp.utils.service.TokenUtilsService;

@Component
public class TokenVerification {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private ReaderService readerService;

	@Autowired
	private TokenUtilsService tokenUtilsService;

	private TokenResponse verificationTokenResponse;

	public TokenResponse verifyToken(Reader reader, String token) {

		TokenData tokenData = new TokenData(reader.getId(), token, TokenType.VERIFICATION_TOKEN);

		Token tokenObj = tokenService.getTokenByCredentials(tokenData);

		if (tokenObj == null)
			verificationTokenResponse = TokenResponse.INVALID_TOKEN;
		else 
			determineTokenExpiration(tokenObj, reader);
	
		return verificationTokenResponse;
	}

	private void determineTokenExpiration(Token token, Reader reader) {

		if (tokenUtilsService.isTokenExpiredAndRemoved(token)) {
			verificationTokenResponse = TokenResponse.EXPIRED_TOKEN;
		} else {
			reader.setEmailAuthenticated(true);
			readerService.updateReader(reader);
			tokenService.deleteTokenById(token.getId());

			verificationTokenResponse = TokenResponse.VALID_TOKEN;
		}

	}

}
