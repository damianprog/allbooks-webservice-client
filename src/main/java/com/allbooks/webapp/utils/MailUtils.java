package com.allbooks.webapp.utils;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.service.ProfileService;
import com.allbooks.webapp.service.ReaderService;

@Service
public class MailUtils {

	@Autowired
	private ReaderService readerService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine templateEngine;

	@Value("${spring.mail.username}")
	private String myEmail;

	@Value("${app.url.name}")
	private String urlName;

	private Reader reader;
	private String subject;
	private String methodMapping;
	private String subjectHeader;
	private String subjectMessage;
	private String templateName;
	private TokenType tokenType;

	public void sendMessage() throws MessagingException {
		
		String token = UUID.randomUUID().toString();

		switch (tokenType) {

		case REGISTRATION_CONFIRM:
			readerService.createVerificationToken(reader, token);
			methodMapping = "/reader/registrationConfirm";
			break;
		case CHANGE_PASSWORD:
			profileService.createPasswordToken(reader, token);
			methodMapping = "/profile/changePasswordPage";
			break;
		}

		String recipentAddress = reader.getEmail();
		String confirmationUrl = urlName + methodMapping + "?token=" + token + "&readerId=" + reader.getId();

		Context context = new Context();
		context.setVariable("url", confirmationUrl);
		context.setVariable("subjectHeader", subjectHeader);
		context.setVariable("subjectMessage", subjectMessage);

		String body = templateEngine.process(templateName, context);

		MimeMessage mail = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, true);
		helper.setTo(recipentAddress);
		helper.setReplyTo(myEmail);
		helper.setFrom(myEmail);
		helper.setSubject(subject);
		helper.setText(body, true);

		mailSender.send(mail);
	}

	public enum TokenType {
		CHANGE_PASSWORD, REGISTRATION_CONFIRM;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubjectHeader() {
		return subjectHeader;
	}

	public void setSubjectHeader(String subjectHeader) {
		this.subjectHeader = subjectHeader;
	}

	public String getSubjectMessage() {
		return subjectMessage;
	}

	public void setSubjectMessage(String subjectMessage) {
		this.subjectMessage = subjectMessage;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

}
