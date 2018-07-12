package com.allbooks.webapp.utils.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.utils.entity.MailData;

@Component
public class MailBuilder {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine templateEngine;

	@Value("${app.url.name}")
	private String urlName;
	
	@Value("${spring.mail.username}")
	private String myEmail;
	
	private String recipentAddress;
	
	public MimeMessage createTokenMail(MailData mailData,String methodMapping) throws MessagingException {
		
		Reader reader = mailData.getReader();
		
		String token = mailData.getToken();
		
		this.recipentAddress = reader.getEmail();
		String confirmationUrl = urlName + methodMapping + "?token=" + token + "&readerId="
				+ reader.getId();

		return createMimeMessage(mailData,confirmationUrl);
		
	}

	public MimeMessage createSimpleMail(MailData mailData) throws MessagingException {
		
		Reader reader = mailData.getReader();
		
		this.recipentAddress = reader.getEmail();
		
		return createMimeMessage(mailData,"localhost:8080/reader/main");
		
	}
	
	private MimeMessage createMimeMessage(MailData mailData,String url) throws MessagingException {
		
		Context context = new Context();
		context.setVariable("url", url);
		context.setVariable("subjectHeader", mailData.getSubjectHeader());
		context.setVariable("subjectMessage", mailData.getSubjectMessage());

		String body = templateEngine.process(mailData.getTemplateName(), context);

		MimeMessage mail = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, true);
		helper.setTo(recipentAddress);
		helper.setReplyTo(myEmail);
		helper.setFrom(myEmail);
		helper.setSubject(mailData.getSubject());
		helper.setText(body, true);
		
		return mail;
	}
	
}
