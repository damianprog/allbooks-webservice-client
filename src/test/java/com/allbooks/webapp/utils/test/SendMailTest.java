package com.allbooks.webapp.utils.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import com.allbooks.webapp.Application;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.service.TokenService;
import com.allbooks.webapp.utils.entity.MailData;
import com.allbooks.webapp.utils.entity.MailData.MailType;
import com.allbooks.webapp.utils.mail.MailBuilder;
import com.allbooks.webapp.utils.mail.SendMail;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class SendMailTest {

	@InjectMocks
	private SendMail sendMail;
	
	@Mock
	private JavaMailSender mailSenderMock;

	@Mock
	private TokenService tokenServiceMock;

	@Mock
	private MailBuilder mailBuilderMock;

	@Mock
	private MailData mailDataMock;
	
	@Mock
	private Reader readerMock;
	
	@Mock
	private MimeMessage mimeMessageMock;
	
	@Test
	public void sendRegistrationConfirmTest() throws MessagingException {
		
		String registrationToken = "";
		
		String methodMapping = "/readerAccount/registrationConfirm";
		
		when(mailDataMock.getToken()).thenReturn(registrationToken);
		
		when(mailDataMock.getReader()).thenReturn(readerMock);
		
		when(mailDataMock.getMailType()).thenReturn(MailType.REGISTRATION_CONFIRM);
		
		when(mailBuilderMock.createTokenMail(mailDataMock, methodMapping)).thenReturn(mimeMessageMock);
		
		sendMail.send(mailDataMock);
		
		verify(mailDataMock).getToken();
		verify(mailDataMock).getReader();
		verify(mailDataMock).getMailType();
		
		verify(tokenServiceMock).createVerificationToken(readerMock, registrationToken);
		
		verify(mailBuilderMock).createTokenMail(mailDataMock, methodMapping);
		
		verify(mailSenderMock).send(mimeMessageMock);
		
		
	}
	
	@Test
	public void sendPasswordTokenTest() throws MessagingException {
		
		String passwordToken = "";
		
		String methodMapping = "/readerAccount/changePasswordPage";
		
		when(mailDataMock.getToken()).thenReturn(passwordToken);
		when(mailDataMock.getReader()).thenReturn(readerMock);
		
		when(mailDataMock.getMailType()).thenReturn(MailType.CHANGE_PASSWORD);
		
		when(mailBuilderMock.createTokenMail(mailDataMock, methodMapping)).thenReturn(mimeMessageMock);
		
		sendMail.send(mailDataMock);
		
		verify(mailDataMock).getToken();
		verify(mailDataMock).getReader();
		verify(mailDataMock).getMailType();
		
		verify(tokenServiceMock).createPasswordToken(readerMock, passwordToken);
		
		verify(mailBuilderMock).createTokenMail(mailDataMock, methodMapping);

		verify(mailSenderMock).send(mimeMessageMock);
		
	}
	
	@Test
	public void sendInformationMail() throws MessagingException {
		
		when(mailDataMock.getReader()).thenReturn(readerMock);
		
		when(mailDataMock.getMailType()).thenReturn(MailType.INFORMATION);
		
		when(mailBuilderMock.createSimpleMail(mailDataMock)).thenReturn(mimeMessageMock);
		
		sendMail.send(mailDataMock);

		verify(mailDataMock).getReader();
		verify(mailDataMock).getMailType();
		
		verify(mailBuilderMock).createSimpleMail(mailDataMock);
		verify(mailSenderMock).send(mimeMessageMock);
	}
	
}
