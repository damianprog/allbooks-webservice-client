package com.allbooks.webapp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.service.ProfileService;

@Component
public class LoggedUserModelProfileCreator {
	
	@Autowired
	private ProfileService profileService;
	
	public ModelMap createModel(Reader currentReader) {
		
		ModelMap modelMap = new ModelMap();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String loggedReaderName = auth.getName();
		
		if (auth.isAuthenticated()) {

			if (!loggedReaderName.equals(currentReader.getUsername())) {

				modelMap.addAttribute("booFriends", //TODO order of parameters
						profileService.areTheyFriends(loggedReaderName, currentReader.getUsername()));
				modelMap.addAttribute("pending",
						profileService.checkPending(currentReader.getUsername(), loggedReaderName));
				modelMap.addAttribute("invite",true);
			}

			else
				modelMap.addAttribute("friendsInvites", profileService.getFriendsInvites(currentReader.getId()));

			modelMap.addAttribute("principalName", loggedReaderName);
		}
		
		return modelMap;
	}
	
}
