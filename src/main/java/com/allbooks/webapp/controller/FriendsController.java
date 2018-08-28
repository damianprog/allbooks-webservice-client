package com.allbooks.webapp.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.allbooks.webapp.entity.Pending;
import com.allbooks.webapp.entity.PendingRequestResponseData;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Token;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.FriendsService;
import com.allbooks.webapp.service.PendingService;
import com.allbooks.webapp.utils.model.AddFriendsModelCreator;
import com.allbooks.webapp.utils.service.FriendsUtilsService;
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

	@Autowired
	private FriendsUtilsService friendsUtilsService;
	
	@GetMapping("/inviteFriend")
	public String inviteFriend(@RequestParam("pageName") String pageName, @RequestParam("recipentId") int recipentId,
			HttpSession session, RedirectAttributes ra) {

		Pending pending = friendsUtilsService.createPending(recipentId);

		pendingService.savePending(pending);

		if(pageName.equals("profile")) {
			ra.addAttribute("readerId", pending.getRecipent().getId());
			return "redirect:/profile/showProfile";
		}
		else
			return "redirect:/friends/showAddFriends";
	}

	@PostMapping("/acceptOrAbort") 
	public String acceptOrAbort(@RequestParam("pageName") String pageName, PendingRequestResponseData responseData,
			Model theModel, HttpSession session, RedirectAttributes ra) {

		friendsUtilsService.acceptOrAbort(responseData);

		if (pageName.equals("profile")) {
			ra.addAttribute("readerId", contextService.getLoggedReaderId());
			return "redirect:/profile/showProfile";
		} else
			return "redirect:/friends/showFriendsRequests";
	}

	@DeleteMapping("/deleteFriends")
	public String deleteFriends(@RequestParam("pageName") String pageName, @RequestParam("friendId") int friendId,
			Model theModel, HttpSession session, Principal principal, RedirectAttributes ra) {

		int readerId = contextService.getLoggedReaderId();

		friendsUtilsService.deleteFriends(readerId, friendId);

		ra.addAttribute("readerId", readerId);

		if (pageName.equals("profile")) {
			ra.addAttribute("readerId", readerId);
			return "redirect:/profile/showProfile";
		}else
			return "redirect:/friends/showFriends";
	}
	
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
