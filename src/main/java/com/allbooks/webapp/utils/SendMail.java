package com.allbooks.webapp.utils;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.service.TokenService;
import com.allbooks.webapp.utils.entity.MailBuilder;

@Service
public class SendMail {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	TokenService tokenService;

	@Autowired
	private TemplateEngine templateEngine;

	@Value("${spring.mail.username}")
	private String myEmail;

	@Value("${app.url.name}")
	private String urlName;

	public void send(MailBuilder mailBuilder) throws MessagingException {

		String methodMapping = "";
		String token = UUID.randomUUID().toString();

		Reader reader = mailBuilder.getReader();

		switch (mailBuilder.getTokenType()) {

		case REGISTRATION_CONFIRM:
			tokenService.createVerificationToken(reader, token);
			methodMapping = "/readerAccount/registrationConfirm";
			break;
		case CHANGE_PASSWORD:
			tokenService.createPasswordToken(reader, token);
			methodMapping = "/readerAccount/changePasswordPage";
			break;
		}

		String recipentAddress = reader.getEmail();
		String confirmationUrl = urlName + methodMapping + "?token=" + token + "&readerId="
				+ reader.getId();

		Context context = new Context();
		context.setVariable("url", confirmationUrl);
		context.setVariable("subjectHeader", mailBuilder.getSubjectHeader());
		context.setVariable("subjectMessage", mailBuilder.getSubjectMessage());

		String body = templateEngine.process(mailBuilder.getTemplateName(), context);

		MimeMessage mail = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, true);
		helper.setTo(recipentAddress);
		helper.setReplyTo(myEmail);
		helper.setFrom(myEmail);
		helper.setSubject(mailBuilder.getSubject());
		helper.setText(body, true);

		mailSender.send(mail);
	}

}
