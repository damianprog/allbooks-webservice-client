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
	private SecurityContextService securityContextService;

	@Autowired
	private ReadingChallangeBoxCreator readingChallangeBoxCreator;

	public ModelMap createModel(Reader readerOfProfile) {

		ModelMap modelMap = modelMapFactory.createInstance();

		String loggedReaderName = securityContextService.getLoggedReaderUserName();

		if (securityContextService.isReaderAuthenticated()) {

			int loggedReaderId = securityContextService.getLoggedReaderId();

			int readerOfProfileId = readerOfProfile.getId();

			if (loggedReaderId != readerOfProfileId)

				modelMap.addAllAttributes(
						friendsRequestsOptionsModelCreator.createModelMap(loggedReaderId, readerOfProfileId));
			else 
				modelMap.addAttribute("friendsRequests", pendingService.getFriendsInvitesByReaderId(readerOfProfileId));
			
			modelMap.addAllAttributes(readingChallangeBoxCreator.create(readerOfProfile.getId()));
			modelMap.addAttribute("principalName", loggedReaderName);
		}

		return modelMap;
	}

}
