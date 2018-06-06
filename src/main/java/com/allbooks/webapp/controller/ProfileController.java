package com.allbooks.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.allbooks.webapp.service.ProfileService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.ReviewService;
import com.allbooks.webapp.utils.LoggedUserModelProfileCreator;
import com.allbooks.webapp.utils.ReaderBooksHandler;
import com.allbooks.webapp.utils.entity.ReaderBooksState;
import com.allbooks.webapp.utils.service.FriendsService;
import com.allbooks.webapp.utils.service.PhotoServiceImpl;

@Controller
@RequestMapping("/profile")
public class ProfileController {
	
	@Autowired
	private ReaderService readerService;

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private ProfileService profileService;

	@Autowired
	private ReaderBooksHandler readerBooksHandler;

	@Autowired
	private PhotoServiceImpl photoService;

	@Autowired
	private LoggedUserModelProfileCreator loggedUserModelProfileCreator;

	@Autowired
	private FriendsService friendsService;

	@GetMapping("/showProfile")
	public String showProfile(@RequestParam(value = "readerId", required = false) Integer readerId, HttpSession session,
			Model theModel, Principal principal) {

		Reader reader = readerService.getReaderById(readerId);

		ReaderBooksState readerBooksState = readerBooksHandler.getReaderBooksState(reader.getId());

		if (reader.getProfilePhoto() != null)
			theModel.addAttribute("profilePic", photoService.getEncodedImage(reader.getProfilePhoto().getPic()));

		List<Reader> friends = profileService.getReaderFriends(reader.getId());

		theModel.addAttribute("readerReviews", reviewService.getReviewsByReaderId(readerId));
		theModel.addAttribute("details", reader.getDetails());
		theModel.addAttribute("read", readerBooksState.getReaderBooksQuantities().get("read"));
		theModel.addAttribute("currentlyReading", readerBooksState.getCurrentlyReadingBooks().size());
		theModel.addAttribute("currentlyReadingList", readerBooksState.getCurrentlyReadingBooks());
		theModel.addAttribute("wantToRead", readerBooksState.getReaderBooksQuantities().get("wantToRead"));
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

		Pending pending = friendsService.createPending(params);

		profileService.savePending(pending);

		ra.addAttribute("readerId", pending.getRecipentId());
		return "redirect:/profile/showProfile";
	}

	@GetMapping("/acceptOrAbort") // need to change property friendsId
	public String acceptOrAbort(@RequestParam Map<String, String> params, Model theModel, HttpSession session,
			RedirectAttributes ra) {

		friendsService.acceptOrAbort(params);

		ra.addAttribute("readerId", params.get("recipentId"));
		return "redirect:/profile/showProfile";
	}

	@GetMapping("/deleteFriends")
	public String deleteFriends(@RequestParam("readerId") int readerId, @RequestParam("friendId") int friendId,
			Model theModel, HttpSession session, Principal principal, RedirectAttributes ra) {

		friendsService.deleteFriends(readerId, friendId);

		ra.addAttribute("readerId", readerId);
		return "redirect:/profile/showProfile";
	}

	@GetMapping("/showEdit")
	public String showEdit(HttpSession session, Model theModel, Principal principal) {

		Reader reader = readerService.getReaderByUsername(principal.getName());
		Details details;

		details = profileService.getDetails(reader.getId());

		if (details == null) {
			details = new Details();
			details.setReaderId(reader.getId());
		}

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
