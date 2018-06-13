package com.allbooks.webapp.utils;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.factories.ModelMapFactory;
import com.allbooks.webapp.service.FriendsService;
import com.allbooks.webapp.service.PendingService;

@Component
public class LoggedReaderModelProfileCreator {

	@Autowired
	private HttpSession session;

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

			int loggedReaderId = (int) session.getAttribute("readerId");
			
			if (!(loggedReaderId == readerOfProfile.getId())) {

				modelMap.addAttribute("areTheyFriends",
						friendsService.areTheyFriends(loggedReaderId, readerOfProfile.getId()));
				modelMap.addAttribute("pending", pendingService.checkPending(loggedReaderId, readerOfProfile.getId()));
				modelMap.addAttribute("invite", true); //TODO use more descriptive attribute name
				modelMap.addAttribute("isItSenderProfile", pendingService.isItSenderProfile(loggedReaderId,readerOfProfile.getId())); 
			}

			else
				modelMap.addAttribute("friendsInvites", pendingService.getFriendsInvites(readerOfProfile.getId()));

			modelMap.addAttribute("principalName", loggedReaderName);
		}

		return modelMap;
	}

}
