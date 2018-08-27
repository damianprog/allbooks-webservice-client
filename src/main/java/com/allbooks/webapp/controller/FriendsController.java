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
import com.allbooks.webapp.enumeration.TokenType;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.FriendsService;
import com.allbooks.webapp.service.PendingService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.TokenService;
import com.allbooks.webapp.utils.model.FriendsRequestsOptionsModelCreator;
import com.allbooks.webapp.utils.service.PhotoService;
import com.allbooks.webapp.utils.service.TokenUtilsService;

@Controller
@RequestMapping("/friends")
public class FriendsController {

	@Autowired
	private ReaderService readerService;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private SecurityContextService contextService;

	@Autowired
	private FriendsService friendsService;

	@Autowired
	private PendingService pendingService;

	@Autowired
	private FriendsRequestsOptionsModelCreator friendsRequestsOptionsModelCreator;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private TokenUtilsService tokenUtilsService;

	@GetMapping("/showFriends")
	public String showFriends(Model theModel) {

		int readerId = contextService.getLoggedReaderId();

		List<Reader> friends = friendsService.getReaderFriends(readerId);

		photoService.setResizedAndEncodedPhotosInReaders(friends, 80, 80);

		theModel.addAttribute("friends", friends);

		return "friends";
	}

	@GetMapping("/showFriendsRequests")
	public String showFriendsRequests(Model theModel) {

		int readerId = contextService.getLoggedReaderId();

		List<Pending> friendsRequests = pendingService.getFriendsInvitesByReaderId(readerId);

		theModel.addAttribute("friendsRequests", friendsRequests);

		return "friendsRequests";
	}

	@GetMapping("/showAddFriends")
	public String showAddFriends(Model theModel, @RequestParam(value = "username", required = false) String username) {

		int readerId = contextService.getLoggedReaderId();

		Reader searchedReader = null;

		theModel.addAttribute("isAlreadySent", false);

		if (username != null)
			searchedReader = readerService.getReaderByUsername(username);

		if (searchedReader != null) {

			photoService.setResizedAndEncodedPhotoInReader(searchedReader, 80, 80);

			theModel.addAttribute("searchedReader", searchedReader);

			theModel.addAllAttributes(
					friendsRequestsOptionsModelCreator.createModelMap(readerId, searchedReader.getId()));
		}

		if (username != null && searchedReader == null)
			theModel.addAttribute("notFound", true);

		return "addFriends";
	}

	@GetMapping("/getInvitationLink")
	public String getInvitationLink(Model theModel) {

		int readerId = contextService.getLoggedReaderId();

		Reader reader = readerService.getReaderById(readerId);

		Token token = tokenService.getTokenByReaderId(readerId, TokenType.INVITATION_TOKEN);

		boolean isTokenExpired = false;
		
		if (token != null)
			isTokenExpired = tokenUtilsService.isTokenExpiredAndRemoved(token);
		
		if(token == null || isTokenExpired)
			token = tokenUtilsService.createTokenForReader(reader, TokenType.INVITATION_TOKEN);
		
		String tokenUrl = tokenUtilsService.getTokenUrl(token);

		theModel.addAttribute("tokenUrl", tokenUrl);

		return "invitationLink";
	}

	

}
