package com.allbooks.webapp.utils;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.utils.entity.MailBuilder;
import com.allbooks.webapp.utils.entity.OnRegistrationCompleteEvent;
import com.allbooks.webapp.utils.entity.MailBuilder.TokenType;

@Component
public class RegistrationListener implements
ApplicationListener<OnRegistrationCompleteEvent>{
	
	@Autowired
	SendMail sendMail;
	
	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		try {
			this.confirmRegistration(event);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public void confirmRegistration(OnRegistrationCompleteEvent event) throws MessagingException {
		
		MailBuilder mailBuilder = new MailBuilder();
		
		mailBuilder.setReader(event.getReader());
		mailBuilder.setSubject("Registration Confirmation");
		mailBuilder.setTokenType(TokenType.REGISTRATION_CONFIRM);
		mailBuilder.setSubjectHeader("Thanks for joining us!");
		mailBuilder.setSubjectMessage("Click on the link below to confirm your account!");
		mailBuilder.setTemplateName("template");
		
		sendMail.send(mailBuilder);
		
	}
	

}
