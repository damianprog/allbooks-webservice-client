package com.allbooks.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.enumeration.Information;
import com.allbooks.webapp.enumeration.TokenType;
import com.allbooks.webapp.security.SecurityContextService;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.entity.TokenData;
import com.allbooks.webapp.utils.model.InvitingReaderBooksPageModelCreator;
import com.allbooks.webapp.utils.service.SaveService;
import com.allbooks.webapp.validators.InvitationTokenValidatorModelCreator;

@Controller
@RequestMapping("/reader")
public class ReaderController {

	@Autowired
	private ReaderService readerService;

	@Autowired
	private SaveService saveService;

	@Autowired
	private SecurityContextService contextService;

	@Autowired
	private InvitingReaderBooksPageModelCreator invitingReaderBooksPageModelCreator;

	@Autowired
	private InvitationTokenValidatorModelCreator invitationTokenValidatorModelCreator;

	@PostMapping("/saveReader")
	public String saveReader(@Valid @ModelAttribute("reader") Reader reader, BindingResult bindingResult,
			Model theModel, HttpSession session, WebRequest request) {

		if (bindingResult.hasErrors())
			return "account/join";

		if (!readerService.isThisLoginTaken(reader.getUsername())) {
			saveService.saveReader(reader);
			theModel.addAttribute("information", Information.SUCCESSFULLY_REGISTERED);
		} else
			theModel.addAttribute("information", Information.LOGIN_TAKEN);

		return "information";
	}

	@PostMapping("/saveReaderInvitation")
	public String saveReaderInvitation(HttpServletRequest request, Model theModel,
			@ModelAttribute("reader") Reader reader, @RequestParam("invitingReaderId") int invitingReaderId) {

		String password = reader.getPassword();

		if (!readerService.isThisLoginTaken(reader.getUsername())) {
			saveService.saveReader(reader);
			contextService.autologin(reader.getUsername(), password);
			theModel.addAttribute(invitingReaderBooksPageModelCreator.createModel(invitingReaderId));
		} else {
			theModel.addAttribute("information", Information.LOGIN_TAKEN);
			return "information";
		}
		return "account/invitingReaderBooksPage";
	}

	@GetMapping("/invitationTokenValidation")
	public String invitationTokenValidation(Model theModel, TokenData tokenData) {

		tokenData.setTokenType(TokenType.INVITATION_TOKEN);

		theModel.addAllAttributes(invitationTokenValidatorModelCreator.getModelOfValidatedToken(tokenData));

		return "account/invitationJoin";
	}

	@GetMapping("/join")
	public String joinPage(Model theModel) {

		Reader reader = new Reader();
		theModel.addAttribute("reader", reader);

		return "account/join";
	}

	@GetMapping("/successfulRegistrationInformation")
	public String successfulRegistrationInformation(Model theModel) {

		theModel.addAttribute("information", Information.SUCCESSFULLY_REGISTERED);

		return "information";
	}
}
