package com.allbooks.webapp.utils.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.FriendsService;
import com.allbooks.webapp.service.PendingService;

@Component
public class FriendsRequestsOptionsModelCreator {

	@Autowired
	private FriendsService friendsService;

	@Autowired
	private PendingService pendingService;

	@Autowired
	private ModelMapFactory modelMapFactory;

	@Autowired
	private SecurityContextService contextService;

	private ModelMap modelMap;

	private int readerOfProfileId, loggedReaderId;

	public ModelMap createModelMap(int readerOfProfileId) {

		initializeThisFields(readerOfProfileId);

		initializeModelMapIfItsVisit();

		return modelMap;
	}

	private void initializeModelMapIfItsVisit() {
		if (isItOtherReaderVisitingThisProfile(loggedReaderId, readerOfProfileId)) {
			modelMap.addAttribute("isItOtherReaderProfile", true);
			initializeModelMap();
		} else
			modelMap.addAttribute("isItOtherReaderProfile", false);
	}

	private void initializeModelMap() {
		modelMap.addAttribute("areTheyFriends", friendsService.areTheyFriends(loggedReaderId, readerOfProfileId));
		modelMap.addAttribute("pending", pendingService.checkPending(loggedReaderId, readerOfProfileId));
		modelMap.addAttribute("isItSenderProfile", pendingService.isItSenderProfile(loggedReaderId, readerOfProfileId));
	}

	private void initializeThisFields(int readerOfProfileId) {
		this.modelMap = modelMapFactory.createInstance();
		this.readerOfProfileId = readerOfProfileId;
		this.loggedReaderId = contextService.getLoggedReaderId();
	}

	private boolean isItOtherReaderVisitingThisProfile(int loggedReaderId, int readerOfProfileId) {
		return loggedReaderId != readerOfProfileId;
	}

}
