package com.allbooks.webapp.utils.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Token;
import com.allbooks.webapp.enumeration.TokenType;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.TokenService;
import com.allbooks.webapp.utils.service.TokenUtilsService;

@Component
public class InvitationTokenGetter {

	@Autowired
	private ReaderService readerService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private SecurityContextService contextService;

	@Autowired
	private TokenUtilsService tokenUtilsService;

	private Reader loggedReader;

	private Token token;

	public Token get() {

		initializeLoggedReader();

		initializeToken();

		createNewInvitationTokenIfNecessary();

		return token;
	}

	private void initializeLoggedReader() {

		int readerId = contextService.getLoggedReaderId();

		this.loggedReader = readerService.getReaderById(readerId);
	}

	private void initializeToken() {
		this.token = tokenService.getTokenByReaderId(loggedReader.getId(), TokenType.INVITATION_TOKEN);
	}

	private void createNewInvitationTokenIfNecessary() {

		boolean isTokenExpired = checkIfTokenIsExpired();

		if (token == null || isTokenExpired)
			token = tokenUtilsService.createTokenForReader(loggedReader, TokenType.INVITATION_TOKEN);
	}

	private boolean checkIfTokenIsExpired() {
		if (token != null)
			return tokenUtilsService.isTokenExpiredAndRemoved(token);
		else
			return false;
	}

}
