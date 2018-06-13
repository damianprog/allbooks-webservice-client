package com.allbooks.webapp.photos.utils.test;

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
import com.allbooks.webapp.factories.Base64Factory;
import com.allbooks.webapp.photos.utils.Base64Encoder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class Base64EncoderTest {

	@InjectMocks
	private Base64Encoder base64Encoder;
	
	@Mock
	private Base64Factory base64Factory;
	
	private byte[] encodedBase64Bytes;
	
	private byte[] base64ToEncode;
	
	@Test
	public void encodeTest() {
		
		when(base64Factory.encode(base64ToEncode)).thenReturn(encodedBase64Bytes);
		
		base64Encoder.getEncodedImage(base64ToEncode);
		
		verify(base64Factory).encode(base64ToEncode);
		verify(base64Factory).createStringFromBytes(encodedBase64Bytes);
		
	}
	
}
