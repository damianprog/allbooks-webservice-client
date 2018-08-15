package com.allbooks.webapp.utils.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allbooks.webapp.entity.Ban;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.utils.entity.MailData;
import com.allbooks.webapp.utils.entity.MailData.MailType;
import com.allbooks.webapp.utils.mail.SendMail;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private SendMail sendMail;

	@Autowired
	private MailData mailData;

	@Override
	public void sendVerificationToken(Reader reader) throws MessagingException {

		mailData.setReader(reader);
		mailData.setSubject("Registration Confirmation");
		mailData.setMailType(MailType.REGISTRATION_CONFIRM);
		mailData.setSubjectHeader("Thanks for joining us!");
		mailData.setSubjectMessage("Click on the link below to confirm your account!");
		mailData.setTemplateName("template");

		sendMail.send(mailData);

	}

	@Override
	public void sendPasswordChanging(Reader reader) throws MessagingException {

		mailData.setReader(reader);
		mailData.setSubject("Change Password");
		mailData.setMailType(MailType.CHANGE_PASSWORD);
		mailData.setSubjectHeader("Change your password");
		mailData.setSubjectMessage("Click on the link below to change your password");
		mailData.setTemplateName("template");

		sendMail.send(mailData);

	}

	@Override
	public void sendBanInformation(Ban ban) throws MessagingException {

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String expiryDate = formatter.format(ban.getExpiryDate());

		mailData.setReader(ban.getReader());
		mailData.setSubject("Allbooks account ban information");
		mailData.setMailType(MailType.INFORMATION);
		mailData.setSubjectHeader("Your Allbooks account has been banned");
		mailData.setSubjectMessage("Your Allbooks account has been banned the ban expiration date is: "
				+ expiryDate + "\n Reason: " + ban.getText());
		mailData.setTemplateName("template");

		sendMail.send(mailData);

	}

}
