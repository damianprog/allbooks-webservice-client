package com.allbooks.webapp.utils.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.allbooks.webapp.Application;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.enumeration.TokenResponse;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.mail.RegistrationConfirmation;
import com.allbooks.webapp.utils.mail.TokenVerification;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class RegistrationConfirmationTest {

	@InjectMocks
	private RegistrationConfirmation registrationConfirmation;
	
	@Mock
	private ReaderService readerServiceMock;
	
	@Mock
	private TokenVerification tokenVerificationMock;
	
	@Mock
	private Reader readerMock;
	
	private int readerId = 1;
	
	private String token = "";
	
	@Test
	public void verifyConfirmationTest() {
		
		when(readerServiceMock.getReaderById(readerId)).thenReturn(readerMock);
		when(readerMock.isEmailAuthenticated()).thenReturn(false);
		when(tokenVerificationMock.verifyToken(readerMock,token)).thenReturn(TokenResponse.VALID_TOKEN);
		
		Map<String,TokenResponse> map = registrationConfirmation.verifyConfirmation(readerId, token);
		
		assertEquals(map.get("info"),TokenResponse.VALID_TOKEN);
		
		verify(readerServiceMock).getReaderById(readerId);
		verify(readerMock).isEmailAuthenticated();
		verify(tokenVerificationMock).verifyToken(readerMock,token);
		
	} 
}
