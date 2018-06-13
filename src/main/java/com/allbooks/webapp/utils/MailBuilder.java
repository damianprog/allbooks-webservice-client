package com.allbooks.webapp.utils;

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
	
	public MimeMessage createMail(MailData mailData,String methodMapping) throws MessagingException {
		
		Reader reader = mailData.getReader();
		
		String token = mailData.getToken();
		
		String recipentAddress = reader.getEmail();
		String confirmationUrl = urlName + methodMapping + "?token=" + token + "&readerId="
				+ reader.getId();

		Context context = new Context();
		context.setVariable("url", confirmationUrl);
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
