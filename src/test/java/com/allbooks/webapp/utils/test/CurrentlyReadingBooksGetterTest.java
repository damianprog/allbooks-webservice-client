package com.allbooks.webapp.utils.test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.allbooks.webapp.Application;
import com.allbooks.webapp.entity.ReaderBook;
import com.allbooks.webapp.factories.ListFactory;
import com.allbooks.webapp.service.ReaderBookService;
import com.allbooks.webapp.utils.readerbook.CurrentlyReadingBooksGetter;
import com.allbooks.webapp.utils.service.PhotoService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class CurrentlyReadingBooksGetterTest {

	@InjectMocks
	private CurrentlyReadingBooksGetter currentlyReadingBooksGetter;
	
	@Mock
	private ReaderBookService readerBookServiceMock;
	
	@Mock
	private PhotoService photoServiceMock;
	
	@Mock
	private ReaderBook readerBookMock;
	
	@Mock
	private ListFactory listFactory;
	
	@Mock
	private List<Object> currentlyReadingBooksMock;
	
	private int readerId = 1;
		
	private byte[] bookPic = {};
	
	private String encodedBookPic = "";
	
	@Test
	public void getByReaderIdTest() {
		
		List<ReaderBook> readerBooks = new ArrayList<>();
		readerBooks.add(readerBookMock);
		
		when(readerBookServiceMock.getReaderBooks(readerId)).thenReturn(readerBooks);
		when(listFactory.createArrayList()).thenReturn(currentlyReadingBooksMock);
		when(readerBookMock.getShelves()).thenReturn("Currently Reading");
		when(readerBookMock.getBookPic()).thenReturn(bookPic);
		when(photoServiceMock.getEncodedImage(bookPic)).thenReturn(encodedBookPic);
		
		currentlyReadingBooksGetter.getByReaderId(readerId);
		
		verify(readerBookServiceMock).getReaderBooks(readerId);
		verify(readerBookMock).getShelves();
		verify(readerBookMock).getBookPic();
		verify(photoServiceMock).getEncodedImage(bookPic);
		verify(readerBookMock).setEncodedBookPic(encodedBookPic);
		verify(currentlyReadingBooksMock).add(readerBookMock);
		
	}
	
}
