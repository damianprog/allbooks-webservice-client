package com.allbooks.webapp.utils.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.factories.ModelMapFactory;
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

	public ModelMap createModelMap(int loggedReaderId, int otherReaderId) {

		ModelMap modelMap = modelMapFactory.createInstance();
		
		if (loggedReaderId != otherReaderId) {
			modelMap.addAttribute("isItOtherReaderProfile", true);
			
			modelMap.addAttribute("areTheyFriends", friendsService.areTheyFriends(loggedReaderId, otherReaderId));
			modelMap.addAttribute("pending", pendingService.checkPending(loggedReaderId, otherReaderId));
			modelMap.addAttribute("isItSenderProfile", pendingService.isItSenderProfile(loggedReaderId, otherReaderId));
		}
		else
			modelMap.addAttribute("isItOtherReaderProfile", false);
			
		return modelMap;
	}

}
