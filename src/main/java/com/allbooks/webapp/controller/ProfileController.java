package com.allbooks.webapp.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.allbooks.webapp.entity.Details;
import com.allbooks.webapp.entity.Friends;
import com.allbooks.webapp.entity.PasswordToken;
import com.allbooks.webapp.entity.Pending;
import com.allbooks.webapp.entity.ProfilePics;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.entity.Review;
import com.allbooks.webapp.service.ProfileService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.ControllerUtils;
import com.allbooks.webapp.utils.MailUtils;
import com.allbooks.webapp.utils.MailUtils.TokenType;

@Controller
@RequestMapping("/profile")
public class ProfileController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	ReaderService readerService;

	@Autowired
	ProfileService profileService;

	@Autowired
	MailUtils mailUtils;

	@GetMapping("/showProfile")
	public String showProfile(@RequestParam(value = "readerId", required = false) Optional<Integer> readerId,
			@RequestParam(value = "recipentId", required = false) Optional<Integer> recipentId, HttpSession session,
			Model theModel, Principal principal) {

		int read, currentlyReading, wantToRead, readerIdInt, friendsNum;
		read = currentlyReading = wantToRead = readerIdInt = friendsNum = 0;
		Reader reader;
		List<Pending> friendsInvites;
		List<Reader> friends;

		boolean invite, booFriends, pending;
		invite = booFriends = pending = false;

		if (readerId.isPresent()) {
			reader = profileService.getReaderById(readerId.get());
		} else if (recipentId.isPresent()) {
			reader = profileService.getReaderById(recipentId.get());
		} else
			reader = readerService.getReaderByUsername(principal.getName());

		List<ReaderBook> readerBooks = readerService.getReaderBooks(reader.getId());
		List<ReaderBook> currentlyReadingList = new ArrayList<ReaderBook>();
		Details details = reader.getDetails();
		for (ReaderBook readerBook : readerBooks) {

			switch (readerBook.getShelves()) {

			case "Read":
				read++;
				break;

			case "Currently Reading":
				currentlyReading++;
				readerBook.setEncodedBookPic(getEncodedImage(readerBook.getBookPic()));
				currentlyReadingList.add(readerBook);
				break;

			case "Want To Read":
				wantToRead++;
				break;

			}

		}

		if (principal != null) {

			if ((!principal.getName().equals(reader.getUsername()))) {
				Friends checkFriends = profileService.areTheyFriends(principal.getName(), reader.getUsername());
				Pending pendingObject = profileService.getPending(reader.getUsername(), principal.getName());
				if (checkFriends != null)
					booFriends = true;
				else {
					if (pendingObject != null)
						pending = true;

				}
				invite = true;
			}

			else {
				friendsInvites = profileService.getFriendsInvites(reader.getId());
				theModel.addAttribute("friendsInvites", friendsInvites);
			}

			theModel.addAttribute("principalName", principal.getName());
		}

		ProfilePics profilePics = reader.getProfilePics();

		if (profilePics != null) {
			String base64Encoded = getEncodedImage(profilePics.getPic());
			theModel.addAttribute("profilePic", base64Encoded);
		}

		if (!reader.getFriends().isEmpty()) {
			friendsNum = reader.getFriends().size();
			friends = reader.getFriends();
			System.out.println(reader.getFriends().toString());
		} else {
			friends = profileService.getReaderFriends(reader.getId());
			if (friends.isEmpty())
				friends = new ArrayList<>();
			else
				friendsNum = friends.size();
		}

		List<Review> reviews = readerService.getReaderReviews(reader.getUsername());
		reviews.sort(Comparator.comparingInt(Review::getId).reversed());
		theModel.addAttribute("readerReviews", reviews);

		theModel.addAttribute("details", details);
		theModel.addAttribute("read", read);
		theModel.addAttribute("currentlyReading", currentlyReading);
		theModel.addAttribute("currentlyReadingList", currentlyReadingList);
		theModel.addAttribute("wantToRead", wantToRead);
		theModel.addAttribute("reader", reader);
		theModel.addAttribute("invite", invite);
		theModel.addAttribute("booFriends", booFriends);
		theModel.addAttribute("pending", pending);
		theModel.addAttribute("friendsNum", friendsNum);
		theModel.addAttribute("friends", friends);

		if (readerIdInt != 0) {
			theModel.addAttribute("readerId", readerIdInt);
		} else
			theModel.addAttribute("readerId", reader.getId());

		return "profile";
	}

	public static String getEncodedImage(byte[] theEncodedBase64) {

		String base64Encoded = null;

		byte[] encodeBase64 = Base64.getEncoder().encode(theEncodedBase64);
		try {
			base64Encoded = new String(encodeBase64, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return base64Encoded;
	}

	@PostMapping("/profileUpload")
	public String profileUpload(HttpSession session, Model theModel, @RequestParam("file") MultipartFile multipartfile,
			Principal principal) throws IOException {

		if (multipartfile.isEmpty()) {
			return "redirect:/profile/showProfile";
		}

		Reader reader = readerService.getReaderByUsername(principal.getName());

		byte[] bytes = ControllerUtils.convertMultipartImage(multipartfile, 200, 250);

		ProfilePics profilePics = reader.getProfilePics();

		if (profilePics == null) {
			profilePics = new ProfilePics(bytes);
		} else
			profilePics.setPic(bytes);

		reader.setProfilePics(profilePics);
		readerService.updateReader(reader);

		return "redirect:/profile/showProfile";
	}

	@GetMapping("/updateState")
	public String updateState(@RequestParam("bookName") String bookName, @RequestParam("newState") String newState,
			HttpSession session, Model theModel, Principal principal, RedirectAttributes ra) {

		int bookId = readerService.getBookId(bookName);
		Reader reader = readerService.getReaderByUsername(principal.getName());

		readerService.saveNewState(newState, bookId, reader.getId());

		ra.addAttribute("myBooks", true);

		return "redirect:/reader/showMyBooks";

	}

	@GetMapping("/updateDateRead")
	public String updateDateRead(@RequestParam("bookName") String bookName, @RequestParam("dateRead") String dateRead,
			HttpSession session, Model theModel, Principal principal, RedirectAttributes ra) {

		int bookId = readerService.getBookId(bookName);
		Reader reader = readerService.getReaderByUsername(principal.getName());
		readerService.saveReadDate(dateRead, bookId, reader.getId());

		ra.addAttribute("myBooks", true);

		return "redirect:/reader/showMyBooks";
	}

	// temporary method
	@GetMapping("/deleteReader")
	public String deleteReader(@RequestParam("readerId") int readerId, HttpSession session) {

		profileService.deleteReader(readerId);

		return "redirect:/reader/start";
	}

	@GetMapping("/inviteFriend")
	public String inviteFriend(@RequestParam("recipentLogin") String recipentLogin,
			@RequestParam("senderLogin") String senderLogin, HttpSession session, Principal principal,
			RedirectAttributes ra) {

		Integer recipentId = profileService.getReaderId(recipentLogin);
		int senderId = profileService.getReaderId(senderLogin);

		Pending pending = new Pending();
		pending.setRecipentId(recipentId);
		pending.setSenderId(senderId);
		pending.setRecipentLogin(recipentLogin);
		pending.setSenderLogin(senderLogin);

		profileService.savePending(pending);

		ra.addAttribute("recipentId", recipentId);
		return "redirect:/profile/showProfile";
	}

	@GetMapping("/acceptOrAbort") // need to change property friendsId
	public String acceptOrAbort(@RequestParam("acceptOrAbort") String acceptOrAbort,
			@RequestParam("recipentId") String recipentId, @RequestParam("senderId") String senderId,
			@RequestParam("pendingId") String pendingId, Model theModel, HttpSession session) {

		int senderIdInt = Integer.valueOf(senderId);
		int recipentIdInt = Integer.valueOf(recipentId);
		int pendingIdInt = Integer.valueOf(pendingId);

		Reader reader1 = profileService.getReaderById(senderIdInt);
		Reader reader2 = profileService.getReaderById(recipentIdInt);

		if (acceptOrAbort.equals("accept")) {
			Friends friends = new Friends(senderIdInt, recipentIdInt);
			profileService.saveFriends(friends);
			profileService.deletePending(pendingIdInt);

		} else {
			profileService.deletePending(pendingIdInt);
		}

		return "redirect:/profile/showProfile";
	}

	@GetMapping("/deleteFriends")
	public String deleteFriends(@RequestParam("reader2Id") int reader2Id, Model theModel, HttpSession session,
			Principal principal) {

		Reader loggedReader = readerService.getReaderByUsername(principal.getName());

		profileService.deleteFriends(loggedReader.getId(), reader2Id);

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
			Principal principal) {

		Reader reader = readerService.getReaderByUsername(principal.getName());
		reader.setDetails(details);

		readerService.updateReader(reader);

		return "redirect:/profile/showProfile";
	}

	@GetMapping("/changePasswordPage")
	public String changePasswordPage(@RequestParam("token") String token, @RequestParam("readerId") int readerId,
			Model theModel) {

		PasswordToken tokenObj = profileService.getPasswordTokenByCredentials(readerId,token);

		if (tokenObj == null) 
			theModel.addAttribute("invalidToken", true);
			else {
				theModel.addAttribute("changing", true);
				theModel.addAttribute("readerId", readerId);
			}
		
		return "forgot";
	}

	 @PostMapping("/changePassword")
	 public String changePassword(@RequestParam("password") String password,@RequestParam("readerId") int readerId,Model theModel) {
		 
		 Reader reader = profileService.getReaderById(readerId);
		 reader.setPassword(bCryptPasswordEncoder.encode(password));
		 
		 readerService.updateReader(reader);
		 profileService.deletePasswordToken(readerId);
		 
		 theModel.addAttribute("passwordChanged",true);
		 
		 return "saved";
	 }

	@PostMapping("/forgotPassword")
	public String sendPasswordToken(@RequestParam("email") String email, Model theModel) throws MessagingException {

		Reader reader = profileService.getReaderByEmail(email + ".com");

		if (reader == null) {
			theModel.addAttribute("error", true);
			return "forgot";
		}

		PasswordToken token = profileService.getPasswordTokenByReaderId(reader.getId());

		if (token != null) {
			theModel.addAttribute("tokenSent", true);
			return "forgot";
		}

		mailUtils.setReader(reader);
		mailUtils.setSubject("Change Password");
		mailUtils.setTokenType(TokenType.CHANGE_PASSWORD);
		mailUtils.setSubjectHeader("Change your password");
		mailUtils.setSubjectMessage("Click on the link below to change your password");
		mailUtils.setTemplateName("template");

		mailUtils.sendMessage();

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
