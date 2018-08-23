package com.allbooks.webapp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.Token;
import com.allbooks.webapp.enumeration.TokenType;
import com.allbooks.webapp.security.SecurityContextService;

@Component
public class TokenUrlCreator {

	@Autowired
	private SecurityContextService contextService;
	
	@Value("${app.url.name}")
	private String urlName;
	
	private String methodMapping;
	
	public String getTokenUrl(Token token) {
		
		TokenType tokenType = token.getTokenType();
		
		switch(tokenType) {
		
		case VERIFICATION_TOKEN:
			methodMapping = "/readerAccount/registrationConfirm";
			break;
		
		case PASSWORD_TOKEN:
			methodMapping = "/readerAccount/changePasswordPage";
			break;
			
		case INVITATION_TOKEN:
			methodMapping = "/reader/invitationTokenValidation";
			break;
			
		default:
			methodMapping = "";
			break;
		}
		
		return createTokenUrl(token);
		
	}

	private String createTokenUrl(Token token) {

		int tokenReaderId = token.getReader().getId();
		
		return urlName + methodMapping + "?token=" + token.getTokenString() + "&readerId="
				+ tokenReaderId;
		
	}
	
}
