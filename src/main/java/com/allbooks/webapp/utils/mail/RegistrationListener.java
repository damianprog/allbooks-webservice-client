package com.allbooks.webapp.utils.mail;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.allbooks.webapp.utils.entity.OnRegistrationCompleteEvent;
import com.allbooks.webapp.utils.service.EmailService;

@Component
public class RegistrationListener implements
ApplicationListener<OnRegistrationCompleteEvent>{
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		try {
			this.confirmRegistration(event);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public void confirmRegistration(OnRegistrationCompleteEvent event) throws MessagingException {
		
		emailService.sendVerificationToken(event.getReader());
		
	}
	

}
