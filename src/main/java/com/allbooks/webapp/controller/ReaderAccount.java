package com.allbooks.webapp.controller;

import java.security.Principal;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.allbooks.webapp.entity.PasswordToken;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.VerificationToken;
import com.allbooks.webapp.enumeration.PasswordTokenResponses;
import com.allbooks.webapp.enumeration.VerificationTokenResponses;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.TokenService;
import com.allbooks.webapp.utils.entity.MailData;
import com.allbooks.webapp.utils.mail.MailBuilder;
import com.allbooks.webapp.utils.mail.RegistrationConfirmation;
import com.allbooks.webapp.utils.service.EmailService;

@Controller
@RequestMapping("/readerAccount")
public class ReaderAccount {

	@Autowired
	private ReaderService readerService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private RegistrationConfirmation registrationConfirmation;
	
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
			theModel.addAttribute("info", PasswordTokenResponses.EMAIL_ERROR);
			return "forgot";
		}

		PasswordToken token = tokenService.getPasswordTokenByReaderId(reader.getId());

		if (token != null) {
			theModel.addAttribute("info", PasswordTokenResponses.ALREADY_SENT);
			return "forgot";
		}

		emailService.sendPasswordChanging(reader);

		theModel.addAttribute("info", PasswordTokenResponses.TOKEN_SENT);

		return "forgot";
	}

	@GetMapping("/forgot")
	public String showForgotPage(Principal principal) {

		if (principal != null)
			return "redirect:/reader/main";

		return "forgot";
	}
	
	@GetMapping("/registrationConfirm")
	public String registrationConfirm(@RequestParam("token") String token, @RequestParam("readerId") Integer readerId,
			Model theModel, HttpSession session, Principal principal) {

		Map<String, VerificationTokenResponses> map = registrationConfirmation.verifyConfirmation(readerId, token);

		theModel.addAttribute("info", map.get("info"));
		theModel.addAttribute("readerId",readerId);
		
		return "registrationConfirm";
	}

	@PostMapping("/resendVerificationToken")
	public String resendVerificationToken(@RequestParam("readerId") int readerId,Model theModel) throws MessagingException {
		
		Reader reader = readerService.getReaderById(readerId);
		
		emailService.sendVerificationToken(reader);
		
		theModel.addAttribute("info",VerificationTokenResponses.VERIFICATION_TOKEN_RESEND);
		
		return "registrationConfirm";
		
	}
	
	@GetMapping("/accessDenied")
	public String accessDenied() {
		return "accessDenied";
	}
	
}
