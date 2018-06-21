package com.allbooks.webapp.utils.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.allbooks.webapp.Application;
import com.allbooks.webapp.entity.Reader;
import com.allbooks.webapp.entity.VerificationToken;
import com.allbooks.webapp.service.ReaderService;
import com.allbooks.webapp.service.TokenService;
import com.allbooks.webapp.utils.mail.TokenVerification;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class TokenVerificationTest {

	@InjectMocks
	private TokenVerification tokenVerification;
	
	@Mock
	private TokenService tokenServiceMock;

	@Mock
	private ReaderService readerServiceMock;
	
	@Mock
	private Reader readerMock;
	
	@Mock
	private VerificationToken verificationTokenMock;
	
	private int readerId = 1;
	
	private int tokenId = 2;
	
	@Test
	public void verifyTokenTrueTest() {
		
		String token = "";
		
		when(readerMock.getId()).thenReturn(readerId);
		when(tokenServiceMock.getVerificationTokenByReaderId(readerId)).thenReturn(verificationTokenMock);
		when(verificationTokenMock.getToken()).thenReturn(token);
		when(verificationTokenMock.getId()).thenReturn(tokenId);
		
		assertEquals(tokenVerification.verifyToken(readerMock, token),true);
		
		verify(readerMock).getId();
		verify(tokenServiceMock).getVerificationTokenByReaderId(readerId);
		verify(verificationTokenMock).getToken();
		verify(verificationTokenMock).getId();

		verify(readerMock).setEnabled(true);
		verify(readerServiceMock).updateReader(readerMock);
		verify(tokenServiceMock).deleteVerificationTokenById(tokenId);
		
	}
	
	@Test
	public void verifyTokenFalseTest() {
		
		String token = "";
		String matchingToken = "matching";
		
		when(readerMock.getId()).thenReturn(readerId);
		when(tokenServiceMock.getVerificationTokenByReaderId(readerId)).thenReturn(verificationTokenMock);
		when(verificationTokenMock.getToken()).thenReturn(matchingToken);
		
		assertEquals(tokenVerification.verifyToken(readerMock, token),false);
		
		verify(readerMock).getId();
		verify(tokenServiceMock).getVerificationTokenByReaderId(readerId);
		verify(verificationTokenMock).getToken();
		
		verify(verificationTokenMock,times(0)).getId();

		verify(readerMock,times(0)).setEnabled(true);
		verify(readerServiceMock,times(0)).updateReader(readerMock);
		verify(tokenServiceMock,times(0)).deleteVerificationTokenById(tokenId);
		
	}
	
}
