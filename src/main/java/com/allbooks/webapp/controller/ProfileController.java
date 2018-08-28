package com.allbooks.webapp.controller;

import java.io.IOException;
import java.security.Principal;

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
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.model.ProfileModelCreator;
import com.allbooks.webapp.utils.service.PhotoServiceImpl;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	private ReaderService readerService;

	@Autowired
	private PhotoServiceImpl photoService;

	@Autowired
	private ProfileModelCreator profileModelCreator;

	@GetMapping("/showProfile")
	public String showProfile(@RequestParam(value = "readerId") int readerId, HttpSession session, Model theModel,
			Principal principal) {

		theModel.addAllAttributes(profileModelCreator.create(readerId));

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
