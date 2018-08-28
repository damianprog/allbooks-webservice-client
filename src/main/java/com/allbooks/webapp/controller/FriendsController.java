package com.allbooks.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.allbooks.webapp.entity.Pending;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Token;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.FriendsService;
import com.allbooks.webapp.service.PendingService;
import com.allbooks.webapp.utils.model.AddFriendsModelCreator;
import com.allbooks.webapp.utils.service.PhotoService;
import com.allbooks.webapp.utils.service.TokenUtilsService;

@Controller
@RequestMapping("/friends")
public class FriendsController {

	@Autowired
	private PhotoService photoService;

	@Autowired
	private FriendsService friendsService;

	@Autowired
	private PendingService pendingService;

	@Autowired
	private AddFriendsModelCreator addFriendsModelCreator;

	@Autowired
	private SecurityContextService contextService;
	
	@Autowired
	private TokenUtilsService tokenUtilsService;

	@GetMapping("/showFriends")
	public String showFriends(Model theModel) {

		int readerId = contextService.getLoggedReaderId();

		List<Reader> friends = friendsService.getReaderFriends(readerId);

		photoService.setResizedAndEncodedPhotosInReaders(friends, 80, 80);

		theModel.addAttribute("friends", friends);

		return "reader/friends";
	}

	@GetMapping("/showFriendsRequests")
	public String showFriendsRequests(Model theModel) {

		int readerId = contextService.getLoggedReaderId();

		List<Pending> friendsRequests = pendingService.getFriendsInvitesByReaderId(readerId);

		theModel.addAttribute("friendsRequests", friendsRequests);

		return "reader/friendsRequests";
	}

	@GetMapping("/showAddFriends")
	public String showAddFriends(Model theModel, @RequestParam(value = "username", required = false) String username) {

		theModel.addAllAttributes(addFriendsModelCreator.createModel(username));

		return "reader/addFriends";
	}

	@GetMapping("/getInvitationLink")
	public String getInvitationLink(Model theModel) {

		Token token = tokenUtilsService.getOrCreateInvitationToken();
		
		String tokenUrl = tokenUtilsService.getTokenUrl(token);

		theModel.addAttribute("tokenUrl", tokenUrl);

		return "account/invitationLink";
	}

}
