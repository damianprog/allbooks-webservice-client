package com.allbooks.webapp.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.entity.OnRegistrationCompleteEvent;
import com.allbooks.webapp.entity.Reader;

@Component
public class RegistrationListener implements
ApplicationListener<OnRegistrationCompleteEvent>{

	@Autowired
	private ReaderService readerService;
	
	@Autowired
    private JavaMailSender mailSender;
	
	
	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}
	
	public void confirmRegistration(OnRegistrationCompleteEvent event) {
		
		Reader reader = event.getReader();
		String token = UUID.randomUUID().toString();
		readerService.createVerificationToken(reader,token);
		
		String recipentAddress = reader.getEmail();
		String subject = "Registration Confirmation";
		String confirmationUrl = "/reader/registrationConfirm?token=" + token + "&readerId=" + reader.getId();
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipentAddress);
		email.setReplyTo("damian.pukszta@outlook.com");
		email.setFrom("damian.pukszta@outlook.com");
		email.setSubject(subject);
		email.setText( "http://localhost:8080" + confirmationUrl);
		
		mailSender.send(email);
	}
	

}
