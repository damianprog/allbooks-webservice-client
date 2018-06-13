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
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.utils.RegistrationConfirmation;
import com.allbooks.webapp.utils.TokenVerification;

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
		when(readerMock.isEnabled()).thenReturn(false);
		when(tokenVerificationMock.verifyToken(readerMock,token)).thenReturn(true);
		
		Map<String,Boolean> map = registrationConfirmation.verifyConfirmation(readerId, token);
		
		assertEquals(map.get("success"),true);
		assertEquals(map.get("alreadyDone"),false);
		
		verify(readerServiceMock).getReaderById(readerId);
		verify(readerMock).isEnabled();
		verify(tokenVerificationMock).verifyToken(readerMock,token);
		
	} 
}
