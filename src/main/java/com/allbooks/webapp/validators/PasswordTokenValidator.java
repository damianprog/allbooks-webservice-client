package com.allbooks.webapp.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Token;
import com.allbooks.webapp.enumeration.TokenResponse;
import com.allbooks.webapp.enumeration.TokenType;
import com.allbooks.webapp.factories.DataObjectFactory;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.service.TokenService;
import com.allbooks.webapp.utils.entity.TokenData;
import com.allbooks.webapp.utils.token.ExpiredTokenRemover;

@Component
public class PasswordTokenValidator {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private ModelMapFactory modelMapFactory;

	@Autowired
	private ExpiredTokenRemover expiredTokenRemover;

	@Autowired
	private DataObjectFactory dataObjectFactory;

	private ModelMap modelMap;

	private Token tokenObj;

	private boolean isAllowedToChange = false;

	public ModelMap validate(String token, int readerId) {

		TokenData tokenData = dataObjectFactory.createTokenData(readerId, token, TokenType.PASSWORD_TOKEN);

		initializeThisFields(tokenData);

		checkIfUserIsAllowedToChangePasswordIfGivenTokenExists();

		modelMap.addAttribute("isAllowedToChange", isAllowedToChange);

		return modelMap;
	}

	private void initializeThisFields(TokenData tokenData) {
		this.modelMap = modelMapFactory.createInstance();
		this.tokenObj = tokenService.getTokenByCredentials(tokenData);
	}
	
	private void checkIfUserIsAllowedToChangePasswordIfGivenTokenExists() {
		if (tokenObj == null)
			modelMap.addAttribute("information", TokenResponse.INVALID_TOKEN);
		else
			checkTokenExpirationValidity();
	}

	private void checkTokenExpirationValidity() {
		if (expiredTokenRemover.isTokenExpired(tokenObj))
			modelMap.addAttribute("information", TokenResponse.EXPIRED_TOKEN);
		else
			isAllowedToChange = true;
	}

}
