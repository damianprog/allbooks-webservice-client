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
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.service.FriendsService;
import com.allbooks.webapp.service.PendingService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.model.LoggedReaderModelProfileCreator;
import com.allbooks.webapp.utils.service.FriendsUtilsService;
import com.allbooks.webapp.utils.service.PhotoServiceImpl;
import com.allbooks.webapp.utils.service.ReaderBooksServiceImpl;

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
	private ReaderBooksServiceImpl readerBooksServiceImpl;

	@Autowired
	private PhotoServiceImpl photoService;

	@Autowired
	private LoggedReaderModelProfileCreator loggedUserModelProfileCreator;

	@Autowired
	private FriendsService friendsService;

	@Autowired
	private PendingService pendingService;

	@GetMapping("/showProfile")
	public String showProfile(@RequestParam(value = "readerId", required = false) Integer readerId, HttpSession session,
			Model theModel, Principal principal) {

		Reader reader = readerService.getReaderById(readerId);

		Map<String, Integer> readerBooksQuantitiesMap = readerBooksServiceImpl.getReaderBooksQuantities(reader.getId());

		if (reader.getProfilePhoto() != null) 
			theModel.addAttribute("profilePic", photoService.getEncodedImage(reader.getProfilePhoto().getPic()));

		List<Reader> friends = friendsService.getReaderFriends(reader.getId());

		theModel.addAttribute("readerReviews", reviewService.getReviewsByReaderId(readerId));
		theModel.addAttribute("details", reader.getDetails());
		theModel.addAttribute("read", readerBooksQuantitiesMap.get("read"));
		theModel.addAttribute("currentlyReading", readerBooksQuantitiesMap.get("currentlyReading"));
		theModel.addAttribute("wantToRead", readerBooksQuantitiesMap.get("wantToRead"));
		theModel.addAttribute("currentlyReadingList", readerBooksServiceImpl.getCurrentlyReadingBooks(readerId));
		theModel.addAttribute("reader", reader);
		theModel.addAttribute("friendsNum", friends.size());
		theModel.addAttribute("friends", friends);
		theModel.addAllAttributes(loggedUserModelProfileCreator.createModel(reader));

		return "profile";
	}

	@PostMapping("/profileUpload")
	public String profileUpload(HttpSession session, Model theModel, @RequestParam("file") MultipartFile multipartFile,
			Principal principal, RedirectAttributes ra) throws IOException {

		Reader reader = readerService.getReaderByUsername(principal.getName());
		ra.addAttribute("readerId", reader.getId());

		if (multipartFile.isEmpty())
			return "redirect:/profile/showProfile";

		readerService.updateReader(photoService.createProfilePhotoForReader(multipartFile, reader));

		return "redirect:/profile/showProfile";
	}

	// temporary method
	@GetMapping("/deleteReader")
	public String deleteReader(@RequestParam("readerId") int readerId, HttpSession session) {

		readerService.deleteReader(readerId);

		return "redirect:/reader/start";
	}

	@GetMapping("/inviteFriend")
	public String inviteFriend(@RequestParam Map<String, String> params, HttpSession session, RedirectAttributes ra) {

		Pending pending = friendsUtilsService.createPending(params);

		pendingService.savePending(pending);

		ra.addAttribute("readerId", pending.getRecipent().getId());
		return "redirect:/profile/showProfile";
	}

	@PostMapping("/acceptOrAbort") // need to change property friendsId
	public String acceptOrAbort(@RequestParam Map<String, String> params, Model theModel, HttpSession session,
			RedirectAttributes ra) {

		friendsUtilsService.acceptOrAbort(params);

		ra.addAttribute("readerId", params.get("recipentId"));
		return "redirect:/profile/showProfile";
	}

	@DeleteMapping("/deleteFriends")
	public String deleteFriends(@RequestParam("readerId") int readerId, @RequestParam("friendId") int friendId,
			Model theModel, HttpSession session, Principal principal, RedirectAttributes ra) {

		friendsUtilsService.deleteFriends(readerId, friendId);

		ra.addAttribute("readerId", readerId);
		return "redirect:/profile/showProfile";
	}

	@GetMapping("/showEdit")
	public String showEdit(HttpSession session, Model theModel, Principal principal) {

		Reader reader = readerService.getReaderByUsername(principal.getName());
		
		Details details = reader.getDetails();

		if (details == null) 
			details = new Details();

		theModel.addAttribute("reader", reader);
		theModel.addAttribute("details", details);

		return "details";
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
