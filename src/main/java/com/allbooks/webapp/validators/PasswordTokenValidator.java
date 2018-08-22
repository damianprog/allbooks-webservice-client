package com.allbooks.webapp.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Token;
import com.allbooks.webapp.enumeration.TokenType;
import com.allbooks.webapp.enumeration.TokenResponse;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.service.TokenService;
import com.allbooks.webapp.utils.entity.TokenData;

@Component
public class PasswordTokenValidator {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private ModelMapFactory modelMapFactory;
	
	@Autowired
	private ExpiredTokenRemover expiredTokenRemover;
	
	public ModelMap validate(String token,int readerId) {
		
		ModelMap modelMap = modelMapFactory.createInstance();
		
		TokenData tokenData = new TokenData(readerId,token,TokenType.PASSWORD_TOKEN);
		
		Token tokenObj = tokenService.getTokenByCredentials(tokenData);

		boolean isAllowedToChange = false;
		
		if (tokenObj == null)
			modelMap.addAttribute("information", TokenResponse.INVALID_TOKEN);
		else {
			if (expiredTokenRemover.isTokenExpired(tokenObj)) 
				modelMap.addAttribute("information",TokenResponse.EXPIRED_TOKEN);
			else {
				isAllowedToChange = true;
			}
		}

		modelMap.addAttribute("isAllowedToChange",isAllowedToChange);
		
		return modelMap;
	}
	
}
