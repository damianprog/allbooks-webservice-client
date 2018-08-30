	package com.allbooks.webapp.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Token;
import com.allbooks.webapp.exceptions.entity.ExpiredInvitationToken;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.factories.ReaderFactory;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.TokenService;
import com.allbooks.webapp.utils.entity.TokenData;
import com.allbooks.webapp.utils.service.TokenUtilsService;

@Component
public class InvitationTokenValidatorModelCreator {

	@Autowired
	private ModelMapFactory modelMapFactory;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private TokenUtilsService tokenUtilsService;

	@Autowired
	private ReaderBookService readerBookService;

	@Autowired
	private ReaderService readerService;

	@Autowired
	private ReaderFactory readerFactory;

	private Reader invitingReader;

	private ModelMap modelMap;

	public ModelMap getModelOfValidatedToken(TokenData tokenData) {

		initializeThisFields(tokenData);

		checkTokenValidity(tokenData);

		createInvitationJoinModel();

		return modelMap;
	}

	private void initializeThisFields(TokenData tokenData) {
		this.invitingReader = readerService.getReaderById(tokenData.getReaderId());
		this.modelMap = modelMapFactory.createInstance();
	}

	private void checkTokenValidity(TokenData tokenData) {

		Token token = tokenService.getTokenByCredentials(tokenData);

		if (token == null || tokenUtilsService.isTokenExpiredAndRemoved(token))
			throw new ExpiredInvitationToken();
	}

	private void createInvitationJoinModel() {
		modelMap.addAttribute("readerBooksList", readerBookService.getReaderBooks(invitingReader.getId()));
		modelMap.addAttribute("invitingReader", invitingReader);
		modelMap.addAttribute("reader", readerFactory.createInstance());
	}
	
}
