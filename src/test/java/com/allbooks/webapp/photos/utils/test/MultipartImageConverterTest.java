package com.allbooks.webapp.photos.utils.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.allbooks.webapp.Application;
import com.allbooks.webapp.factories.FileFactory;
import com.allbooks.webapp.utils.photos.MultipartImageConverter;
import com.allbooks.webapp.utils.photos.MultipartToFile;
import com.allbooks.webapp.utils.photos.ResizePhoto;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class MultipartImageConverterTest {

	@InjectMocks
	private MultipartImageConverter multipartImageConverter;
	
	@Mock
	private FileFactory fileFactory;
	
	@Mock
	private MultipartToFile multipartToFileMock;

	@Mock
	private ResizePhoto resizePhotoMock;
	
	@Mock
	private File convFileMock;
	
	private byte[] convFile;
	
	@Mock
	private MultipartFile multipartFileMock;
	
	@Test
	public void convertTest() throws IOException {
		
		when(multipartToFileMock.convert(multipartFileMock)).thenReturn(convFileMock);
		when(fileFactory.fileToBytes(convFileMock)).thenReturn(convFile);
		
		multipartImageConverter.convert(multipartFileMock);
		
		verify(multipartToFileMock).convert(multipartFileMock);
		verify(fileFactory).fileToBytes(convFileMock);
	}	
}
