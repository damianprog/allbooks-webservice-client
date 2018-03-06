package com.allbooks.webapp.service;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import com.allbooks.webapp.entity.OnRegistrationCompleteEvent;
import com.allbooks.webapp.utils.MailUtils;
import com.allbooks.webapp.utils.MailUtils.TokenType;

@Component
public class RegistrationListener implements
ApplicationListener<OnRegistrationCompleteEvent>{
	
	@Autowired
	MailUtils mailUtils;
	
	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		try {
			this.confirmRegistration(event);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public void confirmRegistration(OnRegistrationCompleteEvent event) throws MessagingException {
		
		mailUtils.setReader(event.getReader());
		mailUtils.setSubject("Registration Confirmation");
		mailUtils.setTokenType(TokenType.REGISTRATION_CONFIRM);
		mailUtils.setSubjectHeader("Thanks for joining us!");
		mailUtils.setSubjectMessage("Click on the link below to confirm your account!");
		mailUtils.setTemplateName("template");
		
		mailUtils.sendMessage();
		
	}
	

}
