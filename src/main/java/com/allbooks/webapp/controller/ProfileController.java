package com.allbooks.webapp.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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

import org.apache.commons.io.FileUtils;
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

		int read, currentlyReading, wantToRead, readerIdInt;
		read = currentlyReading = wantToRead = readerIdInt = 0;
		Reader reader;
		List<Friends> friendsInvites, friends;
		boolean invite, booFriends, pending;
		invite = booFriends = pending = false;

		if (readerId != null) {
			readerIdInt = Integer.valueOf(readerId);
			reader = profileService.getReaderById(readerIdInt);
		} else if ((readerId == null) && (session.getAttribute("readerId") != null)) {
			int gotenReaderId = profileService.getReaderId((String) session.getAttribute("readerId"));
			reader = (Reader) profileService.getReaderById(gotenReaderId);
			session.removeAttribute("readerId");
		} else
			reader = readerService.getReaderByUsername(principal.getName());

		List<ReaderBook> readerBooks = readerService.getReaderBooks(reader.getId());
		List<ReaderBook> currentlyReadingList = new ArrayList<ReaderBook>();
		Details details = profileService.getDetails(reader.getId());
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
				if (checkFriends != null) {
					if (checkFriends.getAccept().equals("false"))
						pending = true;
					else if (checkFriends.getAccept().equals("true"))
						booFriends = true;
				} else
					booFriends = false;
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

		// friends list for reader
		friends = profileService.getReaderFriends(reader.getId());

		ProfilePics profilePics = profileService.getProfilePics(reader.getId());

		if (profilePics != null) {
			String base64Encoded = getEncodedImage(profilePics.getPic());
			theModel.addAttribute("profilePic", base64Encoded);
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
		theModel.addAttribute("friends", friends);
		theModel.addAttribute("friendsNum", friends.size());

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

	@GetMapping("/inviteFriend")
	public String inviteFriend(@RequestParam("reader1login") String reader1Login,
			@RequestParam("reader2login") String reader2Login, HttpSession session) {

		int reader1Id = profileService.getReaderId(reader1Login);
		int reader2Id = profileService.getReaderId(reader2Login);

		Friends friends = new Friends();
		friends.setReader1(reader1Id);
		friends.setReader2(reader2Id); // sender
		friends.setReader1Login(reader1Login);
		friends.setReader2Login(reader2Login);
		friends.setAccept("false");

		profileService.saveFriends(friends);

		session.setAttribute("readerId", reader1Login);
		return "redirect:/profile/showProfile";
	}

	@GetMapping("/acceptOrAbort")
	public String acceptOrAbort(@RequestParam("acceptOrAbort") String acceptOrAbort,
			@RequestParam("readerProfile") String readerId, @RequestParam("friendsId") String friendsId, Model theModel,
			HttpSession session) {

		int friendsIdInt = Integer.valueOf(friendsId);
		int readerIdInt = Integer.valueOf(readerId);

		if (acceptOrAbort.equals("accept")) {
			Friends friends = profileService.getFriendsById(friendsIdInt);
			friends.setAccept("true");
			profileService.saveFriends(friends);
		} else {
			profileService.deleteFriends(friendsIdInt);
		}

		return "redirect:/profile/showProfile";
	}

	@GetMapping("/deleteFriends")
	public String deleteFriends(@RequestParam("friendsId") int friendsId, Model theModel, HttpSession session) {

		profileService.deleteFriends(friendsId);

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

		profileService.saveDetails(details);

		return "redirect:/profile/showProfile";
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

			BufferedImage resized = resize(bimg, 150, 200);

			// SAVED BUFFERED IMAGE
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(resized, "jpg", baos);
			byte[] bytes = baos.toByteArray();

			ProfilePics profilePics = profileService.getProfilePics(reader.getId());

			if (profilePics == null)
				profilePics = new ProfilePics(reader.getId(), bytes);
			else
				profilePics.setPic(bytes);

			profileService.saveProfilePics(profilePics, reader.getId());

		} catch (IOException e) {
			e.printStackTrace();
		}

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
