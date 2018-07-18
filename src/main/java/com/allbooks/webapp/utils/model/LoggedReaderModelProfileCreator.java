package com.allbooks.webapp.utils.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.FriendsService;
import com.allbooks.webapp.service.PendingService;

@Component
public class LoggedReaderModelProfileCreator {

	@Autowired
	private FriendsService friendsService;

	@Autowired
	private PendingService pendingService;
	
	@Autowired
	private ModelMapFactory modelMapFactory;
	
	@Autowired
	private SecurityContextService securityContextService;
	
	public ModelMap createModel(Reader readerOfProfile) {

		ModelMap modelMap = modelMapFactory.createInstance();

		String loggedReaderName = securityContextService.getLoggedReaderUserName();

		if (securityContextService.isReaderAuthenticated()) {

			int loggedReaderId = securityContextService.getLoggedReaderId();
			
			int readerOfProfileId = readerOfProfile.getId();
			
			if (!(loggedReaderId == readerOfProfileId)) {

				modelMap.addAttribute("areTheyFriends",
						friendsService.areTheyFriends(loggedReaderId, readerOfProfileId));
				modelMap.addAttribute("pending", pendingService.checkPending(loggedReaderId, readerOfProfileId));
				modelMap.addAttribute("invite", true); //TODO use more descriptive attribute name
				modelMap.addAttribute("isItSenderProfile", pendingService.isItSenderProfile(loggedReaderId,readerOfProfileId)); 
			}

			else
				modelMap.addAttribute("friendsInvites", pendingService.getFriendsInvites(readerOfProfileId));

			modelMap.addAttribute("principalName", loggedReaderName);
		}

		return modelMap;
	}

}
