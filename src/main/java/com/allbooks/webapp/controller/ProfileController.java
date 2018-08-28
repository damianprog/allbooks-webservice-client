package com.allbooks.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.allbooks.webapp.entity.Details;
import com.allbooks.webapp.entity.Pending;
import com.allbooks.webapp.entity.PendingRequestResponseData;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.FriendsService;
import com.allbooks.webapp.service.PendingService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.model.LoggedReaderModelProfileCreator;
import com.allbooks.webapp.utils.service.FriendsUtilsService;
import com.allbooks.webapp.utils.service.PhotoServiceImpl;
import com.allbooks.webapp.utils.service.ReaderBooksUtilsService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	private ReaderService readerService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private FriendsUtilsService friendsUtilsService;

	@Autowired
	private ReaderBooksUtilsService readerBooksUtilsService;

	@Autowired
	private PhotoServiceImpl photoService;

	@Autowired
	private LoggedReaderModelProfileCreator loggedReaderModelProfileCreator;

	@Autowired
	private FriendsService friendsService;

	@Autowired
	private PendingService pendingService;

	@Autowired
	private SecurityContextService contextService;

	@GetMapping("/showProfile")
	public String showProfile(@RequestParam(value = "readerId", required = false) Integer readerId, HttpSession session,
			Model theModel, Principal principal) {

		Reader reader = readerService.getReaderById(readerId);

		Map<String, Integer> readerBooksQuantitiesMap = readerBooksUtilsService
				.getReaderBooksQuantities(reader.getId());

		if (reader.getProfilePhoto() != null)
			theModel.addAttribute("profilePic", photoService.getEncodedImage(reader.getProfilePhoto()));

		List<Reader> friends = friendsService.getReaderFriends(reader.getId());

		List<Review> reviewsList = reviewService.getLatestReaderReviews(readerId);

		photoService.encodeAndResizeBookPhotoInBookChildren(reviewsList, 80, 87);

		theModel.addAttribute("readerReviews", reviewsList);
		theModel.addAttribute("details", reader.getDetails());
		theModel.addAttribute("read", readerBooksQuantitiesMap.get("read"));
		theModel.addAttribute("currentlyReading", readerBooksQuantitiesMap.get("currentlyReading"));
		theModel.addAttribute("wantToRead", readerBooksQuantitiesMap.get("wantToRead"));
		theModel.addAttribute("currentlyReadingList", readerBooksUtilsService.getCurrentlyReadingBooks(readerId));
		theModel.addAttribute("reader", reader);
		theModel.addAttribute("friendsNum", friends.size());
		theModel.addAttribute("friends", friends);
		theModel.addAllAttributes(loggedReaderModelProfileCreator.createModel(reader));

		return "reader/profile";
	}

	@PostMapping("/profileUpload")
	public String profileUpload(HttpSession session, Model theModel, @RequestParam("file") MultipartFile multipartFile,
			Principal principal, RedirectAttributes ra) throws IOException {

		Reader reader = readerService.getReaderByUsername(principal.getName());
		ra.addAttribute("readerId", reader.getId());

		if (multipartFile.isEmpty())
			return "redirect:/profile/showProfile";

		photoService.createProfilePhotoForReader(multipartFile, reader);
		
		readerService.updateReader(reader);

		return "redirect:/profile/showProfile";
	}

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

		if (pageName.equals("profile"))
			return "redirect:/profile/showProfile";
		else if (pageName.equals("friends"))
			return "redirect:/friends/showFriends";
		else
			return "redirect:/friends/showFriends";
	}

	@GetMapping("/showEdit")
	public String showEdit(HttpSession session, Model theModel, Principal principal) {

		Reader reader = readerService.getReaderByUsername(principal.getName());

		Details details = reader.getDetails();

		if (details == null)
			details = new Details();

		theModel.addAttribute("reader", reader);
		theModel.addAttribute("details", details);

		return "reader/details";
	}

	@PostMapping("/saveDetails")
	public String saveDetails(HttpSession session, Model theModel, @ModelAttribute("details") Details details,
			Principal principal, RedirectAttributes ra) {

		Reader reader = readerService.getReaderByUsername(principal.getName());
		reader.setDetails(details);

		readerService.updateReader(reader);

		ra.addAttribute("readerId", reader.getId());
		return "redirect:/profile/showProfile";
	}

}
