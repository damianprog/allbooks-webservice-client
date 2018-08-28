package com.allbooks.webapp.controller;

import java.security.Principal;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.Token;
import com.allbooks.webapp.enumeration.Information;
import com.allbooks.webapp.enumeration.TokenResponse;
import com.allbooks.webapp.enumeration.TokenType;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.TokenService;
import com.allbooks.webapp.utils.mail.RegistrationConfirmation;
import com.allbooks.webapp.utils.service.EmailService;
import com.allbooks.webapp.validators.PasswordTokenValidator;

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
	
	@Autowired
	private PasswordTokenValidator passwordTokenValidator;
	
	@GetMapping("/changePasswordPage")
	public String changePasswordPage(@RequestParam("token") String token, @RequestParam("readerId") int readerId,
			Model theModel) {

		theModel.addAllAttributes(passwordTokenValidator.validate(token, readerId));

		theModel.addAttribute("readerId", readerId);
		
		return "account/changePassword";
	}

	@PostMapping("/changePassword")
	public String changePassword(@RequestParam("password") String password, @RequestParam("readerId") int readerId,
			Model theModel) {

		Reader reader = readerService.getReaderById(readerId);
		reader.setPassword(bCryptPasswordEncoder.encode(password));

		readerService.updateReader(reader);
		tokenService.deleteTokenByReaderId(readerId,TokenType.PASSWORD_TOKEN);

		theModel.addAttribute("information", Information.PASSWORD_CHANGED);

		return "information";
	}

	@PostMapping("/forgotPassword")
	public String sendPasswordToken(@RequestParam("email") String email, Model theModel) throws MessagingException {

		Reader reader = readerService.getReaderByEmail(email + ".com");

		if (reader == null) {
			theModel.addAttribute("information", TokenResponse.EMAIL_ERROR);
			return "forgot";
		}

		Token token = tokenService.getTokenByReaderId(reader.getId(),TokenType.PASSWORD_TOKEN);

		if (token != null) {
			theModel.addAttribute("information", TokenResponse.ALREADY_SENT);
			return "forgot";
		}

		emailService.sendPasswordChanging(reader);

		theModel.addAttribute("information", TokenResponse.TOKEN_SENT);

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

		Map<String, TokenResponse> map = registrationConfirmation.verifyConfirmation(readerId, token);

		theModel.addAttribute("information", map.get("information"));
		theModel.addAttribute("readerId",readerId);
		
		return "account/registrationConfirm";
	}

	@PostMapping("/resendVerificationToken")
	public String resendVerificationToken(@RequestParam("readerId") int readerId,Model theModel) throws MessagingException {
		
		Reader reader = readerService.getReaderById(readerId);
		
		emailService.sendVerificationToken(reader);
		
		theModel.addAttribute("information",TokenResponse.VERIFICATION_TOKEN_RESEND);
		
		return "account/registrationConfirm";
		
	}
	
	@GetMapping("/accessDenied")
	public String accessDenied(Model theModel) {
		
		theModel.addAttribute("accessDenied",true);
		
		return "information";
	}
	
}
