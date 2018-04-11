package com.allbooks.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.allbooks.webapp.entity.PasswordToken;
import com.allbooks.webapp.entity.Pending;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.ProfileService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.TokenService;
import com.allbooks.webapp.utils.LoggedUserModelProfileCreator;
import com.allbooks.webapp.utils.ReaderBooksHandler;
import com.allbooks.webapp.utils.SendMail;
import com.allbooks.webapp.utils.entity.ReaderBooksState;
import com.allbooks.webapp.utils.service.EmailService;
import com.allbooks.webapp.utils.service.FriendsService;
import com.allbooks.webapp.utils.service.PhotoServiceImpl;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	ReaderService readerService;

	@Autowired
	BookService bookService;

	@Autowired
	ProfileService profileService;

	@Autowired
	TokenService tokenService;

	@Autowired
	SendMail sendMail;

	@Autowired
	ReaderBooksHandler readerBooksHandler;

	@Autowired
	PhotoServiceImpl photoService;

	@Autowired
	LoggedUserModelProfileCreator loggedUserModelProfileCreator;

	@Autowired
	FriendsService friendsService;

	@Autowired
	EmailService emailService;

	@GetMapping("/showProfile")
	public String showProfile(@RequestParam(value = "readerId", required = false) Integer readerId, HttpSession session,
			Model theModel, Principal principal) {

		Reader reader = readerService.getReaderById(readerId);

		ReaderBooksState readerBooksState = readerBooksHandler
				.getReaderBooksState(bookService.getReaderBooks(reader.getId()));

		if (reader.getProfilePhoto() != null)
			theModel.addAttribute("profilePic", photoService.getEncodedImage(reader.getProfilePhoto().getPic()));

		List<Reader> friends = profileService.getReaderFriends(reader.getId());

		theModel.addAttribute("readerReviews", readerService.getReviewsByUsername(reader.getUsername()));
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

	@GetMapping("/updateState")
	public String updateState(@RequestParam("bookName") String bookName, @RequestParam("newState") String newState,
			HttpSession session, Model theModel, Principal principal, RedirectAttributes ra) {

		int bookId = bookService.getBookId(bookName);
		Reader reader = readerService.getReaderByUsername(principal.getName());

		bookService.updateReaderBookShelves(newState, bookId, reader.getId());

		ra.addAttribute("readerId", reader.getId());

		return "redirect:/reader/showMyBooks";

	}

	@GetMapping("/updateDateRead")
	public String updateDateRead(@RequestParam("bookName") String bookName, @RequestParam("dateRead") String dateRead,
			HttpSession session, Model theModel, Principal principal, RedirectAttributes ra) {

		int bookId = bookService.getBookId(bookName);
		Reader reader = readerService.getReaderByUsername(principal.getName());
		bookService.saveReadDate(dateRead, bookId, reader.getId());

		ra.addAttribute("readerId", reader.getId());

		return "redirect:/reader/showMyBooks";
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
	public String deleteFriends(@RequestParam Map<String, Integer> params, Model theModel, HttpSession session,
			Principal principal, RedirectAttributes ra) {

		friendsService.deleteFriends(params);

		ra.addAttribute("readerId", params.get("loggedReaderId"));
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

	@GetMapping("/changePasswordPage")
	public String changePasswordPage(@RequestParam("token") String token, @RequestParam("readerId") int readerId,
			Model theModel) {

		PasswordToken tokenObj = tokenService.getPasswordTokenByCredentials(readerId, token);

		if (tokenObj == null)
			theModel.addAttribute("invalidToken", true);
		else {
			theModel.addAttribute("changing", true);
			theModel.addAttribute("readerId", readerId);
		}

		return "forgot";
	}

	@PostMapping("/changePassword")
	public String changePassword(@RequestParam("password") String password, @RequestParam("readerId") int readerId,
			Model theModel) {

		Reader reader = readerService.getReaderById(readerId);
		reader.setPassword(bCryptPasswordEncoder.encode(password));

		readerService.updateReader(reader);
		tokenService.deletePasswordTokenByReaderId(readerId);

		theModel.addAttribute("passwordChanged", true);

		return "saved";
	}

	@PostMapping("/forgotPassword")
	public String sendPasswordToken(@RequestParam("email") String email, Model theModel) throws MessagingException {

		Reader reader = readerService.getReaderByEmail(email + ".com");

		if (reader == null) {
			theModel.addAttribute("error", true);
			return "forgot";
		}

		PasswordToken token = tokenService.getPasswordTokenByReaderId(reader.getId());

		if (token != null) {
			theModel.addAttribute("tokenSent", true);
			return "forgot";
		}

		emailService.sendPasswordChanging(reader);

		theModel.addAttribute("success", true);

		return "forgot";
	}

	@GetMapping("/forgot")
	public String showForgotPage(Principal principal) {

		if (principal != null)
			return "redirect:/reader/main";

		return "forgot";
	}
}
