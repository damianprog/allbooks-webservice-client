package com.allbooks.webapp.utils.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.PendingService;
import com.allbooks.webapp.utils.ReadingChallangeBoxCreator;

@Component
public class LoggedReaderModelProfileCreator {

	@Autowired
	private FriendsRequestsOptionsModelCreator friendsRequestsOptionsModelCreator;

	@Autowired
	private PendingService pendingService;

	@Autowired
	private ModelMapFactory modelMapFactory;

	@Autowired
	private SecurityContextService contextService;

	@Autowired
	private ReadingChallangeBoxCreator readingChallangeBoxCreator;

	private ModelMap modelMap;

	private int readerOfProfileId, loggedReaderId;

	public ModelMap createModel(Reader readerOfProfile) {

		intializeThisFields(readerOfProfile);

		initializeModelMapIfReaderIsAuthenticated();

		return modelMap;
	}

	private void intializeThisFields(Reader readerOfProfile) {
		this.modelMap = modelMapFactory.createInstance();
		this.readerOfProfileId = readerOfProfile.getId();
		this.loggedReaderId = contextService.getLoggedReaderId();
	}
	
	private void initializeModelMapIfReaderIsAuthenticated() {
		if (contextService.isReaderAuthenticated())
			initializeAuthenticatedReaderModelMap();

	}

	private void initializeAuthenticatedReaderModelMap() {
		if (isItOtherReaderVisitingThisProfile())
			modelMap.addAllAttributes(
					friendsRequestsOptionsModelCreator.createModelMap(readerOfProfileId));
		else
			modelMap.addAttribute("friendsRequests", pendingService.getFriendsInvitesByReaderId(readerOfProfileId));

		modelMap.addAllAttributes(readingChallangeBoxCreator.create(readerOfProfileId));

	}

	private boolean isItOtherReaderVisitingThisProfile() {
		return loggedReaderId != readerOfProfileId;
	}

}
