package com.allbooks.webapp.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;
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

import com.allbooks.webapp.entity.Details;
import com.allbooks.webapp.entity.Friends;
import com.allbooks.webapp.entity.Pending;
import com.allbooks.webapp.entity.ProfilePics;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.service.ProfileService;
import com.allbooks.webapp.service.ReaderService;

import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping("/profile")
public class ProfileController {
	@Autowired
	ReaderService readerService;

	@Autowired
	ProfileService profileService;

	@GetMapping("/showProfile")
	public String showProfile(@RequestParam(value = "readerId", required = false) String readerId, HttpSession session,
			Model theModel, Principal principal) {

		int read, currentlyReading, wantToRead, readerIdInt, friendsNum;
		read = currentlyReading = wantToRead = readerIdInt = friendsNum = 0;
		Reader reader;
		List<Pending> friendsInvites;
		List<Reader> friends;

		boolean invite, booFriends, pending;
		invite = booFriends = pending = false;

		if (readerId != null) {
			readerIdInt = Integer.valueOf(readerId);
			reader = profileService.getReaderById(readerIdInt);
		} else if ((readerId == null) && (session.getAttribute("recipentLogin") != null)) {
			int gotenReaderId = profileService.getReaderId((String) session.getAttribute("recipentLogin"));
			reader = (Reader) profileService.getReaderById(gotenReaderId);
			session.removeAttribute("recipentLogin");
		} else
			reader = readerService.getReaderByUsername(principal.getName());

		List<ReaderBook> readerBooks = readerService.getReaderBooks(reader.getId());
		List<ReaderBook> currentlyReadingList = new ArrayList<ReaderBook>();
		Details details = reader.getDetails();
		for (ReaderBook readerBook : readerBooks) {

			if (readerBook.getShelves().equals("Read"))
				read++;
			else if (readerBook.getShelves().equals("Currently Reading")) {
				currentlyReading++;
				currentlyReadingList.add(readerBook);
			} else if (readerBook.getShelves().equals("Want To Read"))
				wantToRead++;
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

					booFriends = false;
				}
			}

			if ((!principal.getName().equals(reader.getUsername()))) {
				invite = true;
			}
			theModel.addAttribute("principalName", principal.getName());

			if ((principal.getName().equals(reader.getUsername()))) {
				friendsInvites = profileService.getFriendsInvites(reader.getId());
				theModel.addAttribute("friendsInvites", friendsInvites);
			}
		}

		ProfilePics profilePics = reader.getProfilePics();

		if (profilePics != null) {
			String base64Encoded = getEncodedImage(profilePics.getPic());
			theModel.addAttribute("profilePic", base64Encoded);
		}

		if (!reader.getFriends().isEmpty()) {
			friendsNum = reader.getFriends().size();
			friends = reader.getFriends();
		} else {
			friends = profileService.getReaderFriends(reader.getId());
			if (friends.isEmpty())
				friends = new ArrayList<>();
			else
				friendsNum = friends.size();
		}
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

	public String getEncodedImage(byte[] theEncodedBase64) {

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
			Principal principal) {

		if (multipartfile.isEmpty()) {
			return "redirect:/profile/showProfile";
		}

		Reader reader = readerService.getReaderByUsername(principal.getName());

		try {

			File convFile = convert(multipartfile);

			BufferedImage bimg = ImageIO.read(convFile);

			BufferedImage resized = resize(bimg, 200, 250);

			// SAVED BUFFERED IMAGE
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(resized, "jpg", baos);
			byte[] bytes = baos.toByteArray();

			ProfilePics profilePics = reader.getProfilePics(); // ?

			if (profilePics == null) {
				profilePics = new ProfilePics(bytes);
			} else
				profilePics.setPic(bytes);

			reader.setProfilePics(profilePics);
			readerService.updateReader(reader);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/profile/showProfile";
	}
	
	@GetMapping("/updateState")
	public String updateState(@RequestParam("bookName") String bookName, @RequestParam("newState") String newState,
			HttpSession session, Model theModel, Principal principal) {

		int bookId = readerService.getBookId(bookName);
		Reader reader = readerService.getReaderByUsername(principal.getName());

		readerService.saveNewState(newState, bookId, reader.getId());

		List<ReaderBook> readerBooks = readerService.getReaderBooks(reader.getId());

		for (ReaderBook tempReaderBook : readerBooks) {

			int readerRating = readerService.getReaderRating(reader.getId(), tempReaderBook.getMinBookName());
			tempReaderBook.setReaderRating(readerRating);
		}

		theModel.addAttribute("readerBooks", readerBooks);
		theModel.addAttribute("myBooks", true);

		return "mybooks";

	}

	@GetMapping("/updateDateRead")
	public String updateDateRead(@RequestParam("bookName") String bookName, @RequestParam("dateRead") String dateRead,
			HttpSession session, Model theModel, Principal principal) {

		int bookId = readerService.getBookId(bookName);
		Reader reader = readerService.getReaderByUsername(principal.getName());
		readerService.saveReadDate(dateRead, bookId, reader.getId());

		List<ReaderBook> readerBooks = readerService.getReaderBooks(reader.getId());

		for (ReaderBook tempReaderBook : readerBooks) {

			int readerRating = readerService.getReaderRating(reader.getId(), tempReaderBook.getMinBookName());
			tempReaderBook.setReaderRating(readerRating);
		}

		theModel.addAttribute("readerBooks", readerBooks);
		theModel.addAttribute("myBooks", true);

		return "mybooks";
	}

	// temporary method
	@GetMapping("/deleteReader")
	public String deleteReader(@RequestParam("readerId") int readerId, HttpSession session) {

		profileService.deleteReader(readerId);

		return "redirect:/reader/start";
	}

	@GetMapping("/inviteFriend")
	public String inviteFriend(@RequestParam("recipentLogin") String recipentLogin,
			@RequestParam("senderLogin") String senderLogin, HttpSession session, Principal principal) {

		int recipentId = profileService.getReaderId(recipentLogin);
		int senderId = profileService.getReaderId(senderLogin);

		Pending pending = new Pending();
		pending.setRecipentId(recipentId);
		pending.setSenderId(senderId);
		pending.setRecipentLogin(recipentLogin);
		pending.setSenderLogin(senderLogin);

		profileService.savePending(pending);

		session.setAttribute("recipentLogin", recipentLogin);
		return "redirect:/profile/showProfile";
	}

	@GetMapping("/acceptOrAbort") // need to change property friendsId
	public String acceptOrAbort(@RequestParam("acceptOrAbort") String acceptOrAbort,
			@RequestParam("recipentId") String recipentId, @RequestParam("senderId") String senderId,
			@RequestParam("pendingId") String pendingId, Model theModel, HttpSession session) {

		// check if its possible to transfer int type of id's
		int senderIdInt = Integer.valueOf(senderId);
		int recipentIdInt = Integer.valueOf(recipentId);
		int pendingIdInt = Integer.valueOf(pendingId);

		Reader reader1 = profileService.getReaderById(senderIdInt);
		Reader reader2 = profileService.getReaderById(recipentIdInt);

		if (acceptOrAbort.equals("accept")) {
			Friends friends = new Friends(senderIdInt, recipentIdInt);
			friends.setReader1Login(reader1.getUsername());
			friends.setReader2Login(reader2.getUsername());
			profileService.saveFriends(friends);
			profileService.deletePending(pendingIdInt);

		} else {
			profileService.deletePending(pendingIdInt);
		}

		return "redirect:/profile/showProfile";
	}

	@GetMapping("/deleteFriends")
	public String deleteFriends(@RequestParam("friendsId") int reader2Id, Model theModel, HttpSession session,
			Principal principal) {

		Reader loggedReader = readerService.getReaderByUsername(principal.getName());
		
		profileService.deleteFriends(loggedReader.getId(),reader2Id);

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

	public static File convert(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	public static BufferedImage resize(BufferedImage img, int newW, int newH) throws IOException {
		return Thumbnails.of(img).size(newW, newH).asBufferedImage();
	}
}
