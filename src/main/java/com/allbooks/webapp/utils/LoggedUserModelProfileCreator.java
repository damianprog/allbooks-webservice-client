package com.allbooks.webapp.utils;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.service.ProfileService;

@Component
public class LoggedUserModelProfileCreator {

	@Autowired
	HttpSession session;
	
	@Autowired
	ProfileService profileService;
	
	public ModelMap createModel(Reader currentReader) {
		
		ModelMap modelMap = new ModelMap();
		Reader reader = (Reader) session.getAttribute("readerSession");
		
		if (reader != null) {

			if (!reader.getUsername().equals(currentReader.getUsername())) {

				modelMap.addAttribute("booFriends",
						profileService.areTheyFriends(reader.getUsername(), currentReader.getUsername()));
				modelMap.addAttribute("pending",
						profileService.checkPending(currentReader.getUsername(), reader.getUsername()));
				modelMap.addAttribute("invite",true);
			}

			else
				modelMap.addAttribute("friendsInvites", profileService.getFriendsInvites(currentReader.getId()));

			modelMap.addAttribute("principalName", reader.getUsername());
		}
		
		return modelMap;
	}
	
}
