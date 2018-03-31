package com.allbooks.webapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
import com.allbooks.webapp.entity.Friends;
import com.allbooks.webapp.entity.PasswordToken;
import com.allbooks.webapp.entity.Pending;
import com.allbooks.webapp.entity.ProfilePics;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.photos.component.Base64Encoder;
import com.allbooks.webapp.photos.component.MultipartImageConverter;
import com.allbooks.webapp.service.BookService;
import com.allbooks.webapp.service.ProfileService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.TokenService;
import com.allbooks.webapp.utils.ReaderBooksHandler;
import com.allbooks.webapp.utils.SendMail;
import com.allbooks.webapp.utils.entity.MailBuilder;
import com.allbooks.webapp.utils.entity.MailBuilder.TokenType;
import com.allbooks.webapp.utils.service.PhotoServiceImpl;
import com.allbooks.webapp.utils.entity.ReaderBooksState;

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
	
	@GetMapping("/showProfile")
	public String showProfile(@RequestParam(value = "readerId", required = false) Integer readerId, HttpSession session,
			Model theModel, Principal principal) {

		List<Reader> friends = new ArrayList<>();

		boolean invite = false;

		Reader reader = readerService.getReaderById(readerId);

		ReaderBooksState readerBooksState = readerBooksHandler
				.getReaderBooksState(bookService.getReaderBooks(reader.getId()));

		if (principal != null) {

			if (!principal.getName().equals(reader.getUsername())) {

				theModel.addAttribute("booFriends",
						profileService.areTheyFriends(principal.getName(), reader.getUsername()));
				theModel.addAttribute("pending",
						profileService.checkPending(reader.getUsername(), principal.getName()));

				invite = true;
			}

			else
				theModel.addAttribute("friendsInvites", profileService.getFriendsInvites(reader.getId()));

			theModel.addAttribute("principalName", principal.getName());
		}

		if (reader.getProfilePics() != null)
			theModel.addAttribute("profilePic", photoService.getEncodedImage(reader.getProfilePics().getPic()));

		friends = profileService.getReaderFriends(reader.getId());

		List<Review> reviews = readerService.getReaderReviews(reader.getUsername());
		reviews.sort(Comparator.comparingInt(Review::getId).reversed());
		theModel.addAttribute("readerReviews", reviews);

		theModel.addAttribute("details", reader.getDetails());
		theModel.addAttribute("read", readerBooksState.getReaderBooksQuantities().get("read"));
		theModel.addAttribute("currentlyReading", readerBooksState.getCurrentlyReadingBooks().size());
		theModel.addAttribute("currentlyReadingList", readerBooksState.getCurrentlyReadingBooks());
		theModel.addAttribute("wantToRead", readerBooksState.getReaderBooksQuantities().get("wantToRead"));
		theModel.addAttribute("reader", reader);
		theModel.addAttribute("invite", invite);
		theModel.addAttribute("friendsNum", friends.size());
		theModel.addAttribute("friends", friends);

		return "profile";
	}

	@PostMapping("/profileUpload")
	public String profileUpload(HttpSession session, Model theModel, @RequestParam("file") MultipartFile multipartfile,
			Principal principal, RedirectAttributes ra) throws IOException {

		Reader reader = readerService.getReaderByUsername(principal.getName());

		ra.addAttribute("readerId", reader.getId());

		if (multipartfile.isEmpty())
			return "redirect:/profile/showProfile";
		

		byte[] bytes = photoService.convertMultipartImage(multipartfile, 200, 250);

		ProfilePics profilePics = reader.getProfilePics();

		if (profilePics == null) 
			profilePics = new ProfilePics(bytes);
		 else
			profilePics.setPic(bytes);

		reader.setProfilePics(profilePics);
		readerService.updateReader(reader);

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

		ra.addAttribute("myBooks", true);

		return "redirect:/reader/showMyBooks";
	}

	// temporary method
	@GetMapping("/deleteReader")
	public String deleteReader(@RequestParam("readerId") int readerId, HttpSession session) {

		readerService.deleteReader(readerId);

		return "redirect:/reader/start";
	}

	@GetMapping("/inviteFriend")
	public String inviteFriend(@RequestParam("recipentLogin") String recipentLogin,
			@RequestParam("senderLogin") String senderLogin, HttpSession session, Principal principal,
			RedirectAttributes ra) {

		Integer recipentId = readerService.getReaderId(recipentLogin);
		int senderId = readerService.getReaderId(senderLogin);

		Pending pending = new Pending();
		pending.setRecipentId(recipentId);
		pending.setSenderId(senderId);
		pending.setRecipentLogin(recipentLogin);
		pending.setSenderLogin(senderLogin);

		profileService.savePending(pending);

		ra.addAttribute("readerId", recipentId);
		return "redirect:/profile/showProfile";
	}

	@GetMapping("/acceptOrAbort") // need to change property friendsId
	public String acceptOrAbort(@RequestParam("acceptOrAbort") String acceptOrAbort,
			@RequestParam("recipentId") String recipentId, @RequestParam("senderId") String senderId,
			@RequestParam("pendingId") String pendingId, Model theModel, HttpSession session, RedirectAttributes ra) {

		int senderIdInt = Integer.valueOf(senderId);
		int recipentIdInt = Integer.valueOf(recipentId);
		int pendingIdInt = Integer.valueOf(pendingId);

		if (acceptOrAbort.equals("accept")) {
			Friends friends = new Friends(senderIdInt, recipentIdInt);
			profileService.saveFriends(friends);
			profileService.deletePending(pendingIdInt);

		} else {
			profileService.deletePending(pendingIdInt);
		}

		ra.addAttribute("readerId", recipentIdInt);
		return "redirect:/profile/showProfile";
	}

	@GetMapping("/deleteFriends")
	public String deleteFriends(@RequestParam("reader2Id") int reader2Id, Model theModel, HttpSession session,
			Principal principal, RedirectAttributes ra) {

		Reader loggedReader = readerService.getReaderByUsername(principal.getName());

		profileService.deleteFriends(loggedReader.getId(), reader2Id);

		ra.addAttribute("readerId", loggedReader.getId());
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
		tokenService.deletePasswordToken(readerId);

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

		MailBuilder mailBuilder = new MailBuilder();

		mailBuilder.setReader(reader);
		mailBuilder.setSubject("Change Password");
		mailBuilder.setTokenType(TokenType.CHANGE_PASSWORD);
		mailBuilder.setSubjectHeader("Change your password");
		mailBuilder.setSubjectMessage("Click on the link below to change your password");
		mailBuilder.setTemplateName("template");

		sendMail.send(mailBuilder);

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
